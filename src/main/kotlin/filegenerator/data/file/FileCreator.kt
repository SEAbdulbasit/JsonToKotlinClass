package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.repository.SettingsRepository
import filegenerator.model.AndroidComponent

private const val LAYOUT_DIRECTORY = "layout"

interface FileCreator {

    fun createScreenFiles(
        packageName: String,
        screenName: String,
        androidComponent: AndroidComponent,
        psiDirectory: PsiDirectory,
        fileBody: String
    )
}

class FileCreatorImpl constructor(
    private val settingsRepository: SettingsRepository,
    private val project: Project
) : FileCreator {

    override fun createScreenFiles(
        packageName: String,
        screenName: String,
        androidComponent: AndroidComponent,
        psiDirectory: PsiDirectory,
        fileBody: String
    ) {
        settingsRepository.loadScreenElements().forEach {
            val file = File(
                it.fileName(screenName, packageName, androidComponent.displayName), it.body(
                    screenName,
                    packageName,
                    androidComponent.displayName,
                    fileBody
                ), it.fileType
            )
            if (psiDirectory != null) {
                addFile(psiDirectory, file, it.subdirectory.toLowerCase())
            }
        }

    }


    private fun addFile(directory: PsiDirectory, file: File, subdirectory: String) {
        if (subdirectory.isNotEmpty()) {
            var newSubdirectory = directory
            subdirectory.split("/").forEach { segment ->
                newSubdirectory =
                    newSubdirectory.findSubdirectory(segment) ?: newSubdirectory.createSubdirectory(segment)
            }
            DirectoryImpl(project, newSubdirectory).addFile(file)
        } else {
            DirectoryImpl(project, directory).addFile(file)
        }
    }


    fun findCodeSubdirectory(packageName: String, directory: PsiDirectory): PsiDirectory? {
        var subdirectory = directory
        packageName.split(".").forEach {
            subdirectory = subdirectory.findSubdirectory(it) ?: subdirectory.createSubdirectory(it)
        }
        return subdirectory
    }
}
