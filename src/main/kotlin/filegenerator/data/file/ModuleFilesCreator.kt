package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.repository.SettingsRepository

interface ModuleFilesCreator {
    fun createModuleFiles(
        packageName: String,
        psiDirectory: PsiDirectory,
    )

}

class ModuleFileCreatorImpl constructor(
    private val settingsRepository: SettingsRepository, private val project: Project
) : ModuleFilesCreator {

    override fun createModuleFiles(
        packageName: String,
        psiDirectory: PsiDirectory,
    ) {

        settingsRepository.loadModuleElements().forEach {
            var file = File(
                name = it.fileName(
                    screenName = it.name
                ), content = it.template, fileType = it.fileType
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
