package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.repository.SettingsRepository
import filegenerator.model.FileType
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Created by abdulbasit on 01/02/2022.
 */

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

            var mapToItemParams = ""
            var mapFromItemsParams = ""

            when (it.fileType) {
                FileType.ENTITY_MAPPER -> {
                    mapToItemParams =
                        generateFilesFromTemplates.getEntityMappersParams(dataClassParamsWithOutAnnotations, it)
                    mapFromItemsParams = generateFilesFromTemplates.getMapFromItemParamsEntity(mapToItemParams)
                }
                FileType.UI_MAPPER -> {
                    mapToItemParams =
                        generateFilesFromTemplates.getUiMappersParams(dataClassParamsWithOutAnnotations, it)
                    mapFromItemsParams = generateFilesFromTemplates.getMapFromItemParamsUi(mapToItemParams)
                }
            }

            var file = File(
                name = it.fileName(
                    screenName = screenName
                ), content = it.body(
                    screenName = screenName,
                    packageName = packageName,
                    fileBody = fileBodyWithRegexApplied,
                    dataClassParamsWithoutAnnotations = dataClassParamsWithOutAnnotations,
                    mappersDeclaration = mappersDeclaration,
                    mapToParams = mapToItemParams,
                    mapFromParams = mapFromItemsParams
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
