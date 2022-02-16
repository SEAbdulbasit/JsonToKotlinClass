package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.repository.SettingsRepository
import filegenerator.model.Variable

interface ModuleFilesCreator {
    fun createModuleFiles(
        packageName: String,
        psiDirectory: PsiDirectory,
        isAndroidModule: Boolean,
        moduleName: String
    )

}

class ModuleFileCreatorImpl constructor(
    private val settingsRepository: SettingsRepository, private val project: Project
) : ModuleFilesCreator {

    override fun createModuleFiles(
        packageName: String,
        psiDirectory: PsiDirectory,
        isAndroidModule: Boolean,
        moduleName: String
    ) {

        val element = if (isAndroidModule) {
            settingsRepository.loadAndroidModuleElements()
        } else {
            settingsRepository.loadModuleElements()
        }
        element.forEach {
            var file = File(
                name = it.fileName(
                    screenName = it.name
                ), content = it.template.replace(Variable.MODULE_NAME.value, moduleName), fileType = it.fileType
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
