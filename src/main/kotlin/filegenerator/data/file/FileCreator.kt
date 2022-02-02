package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.repository.SettingsRepository
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

interface FileCreator {
    fun createScreenFiles(
        splitDataClass: KotlinClass,
        packageName: String,
        psiDirectory: PsiDirectory,
    )
}

class FileCreatorImpl constructor(
    private val settingsRepository: SettingsRepository, private val project: Project
) : FileCreator {

    override fun createScreenFiles(
        splitDataClass: KotlinClass,
        packageName: String,
        psiDirectory: PsiDirectory,
    ) {

        val generateFilesFromTemplates = GenerateFilesFromTemplates(splitDataClass)

        settingsRepository.loadScreenElements().forEach {
            generateFilesFromTemplates.setMappers(it)
            val screenName = generateFilesFromTemplates.getFileName(it)
            val fileBodyWithRegexApplied = generateFilesFromTemplates.fileBodyWithRegexApplied(it)
            val mappersDeclaration = generateFilesFromTemplates.mappersDeclaration(it)
            val dataClassParamsWithOutAnnotations =
                generateFilesFromTemplates.getDataClassParamsWithOutAnnotations(fileBodyWithRegexApplied)
            val mappersParams = generateFilesFromTemplates.getMappersParams(dataClassParamsWithOutAnnotations, it)

            var file = File(
                name = it.fileName(
                    screenName = screenName
                ), content = it.body(
                    screenName = screenName,
                    packageName = packageName,
                    fileBody = fileBodyWithRegexApplied,
                    dataClassParamsWithoutAnnotations = dataClassParamsWithOutAnnotations,
                    mappersDeclaration = mappersDeclaration,
                    mappersParams = mappersParams
                ), fileType = it.fileType
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
