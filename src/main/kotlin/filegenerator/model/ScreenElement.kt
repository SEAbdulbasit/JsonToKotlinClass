package filegenerator.model

import filegenerator.data.file.getDataClassParamsWithoutAnnotations
import filegenerator.data.file.getMapperBodyParams
import wu.seal.jsontokotlin.utils.KotlinClassFileGenerator
import java.io.Serializable

const val DEFAULT_SOURCE_SET = "main"

data class ScreenElement(
    var name: String = "",
    var template: String = "",
    var fileType: FileType,
    var fileNameTemplate: String = "",
    var subdirectory: String = "",
    var sourceSet: String = DEFAULT_SOURCE_SET
) : Serializable {

    override fun toString() = name

    fun body(
        screenName: String,
        packageName: String,
        fileBody: String,
        mappers: String?,
        mappersIndexes: List<KotlinClassFileGenerator.MappersWithIndex>?,
        elementName: String
    ) = template.replaceVariables(screenName, packageName, fileBody, mappers, mappersIndexes, elementName)

    fun fileName(
        screenName: String,
        packageName: String,
    ) = fileNameTemplate.replaceVariableForFileName(screenName, packageName).run {
        this
    }

    private fun String.replaceVariables(
        screenName: String,
        packageName: String,
        fileBody: String,
        mappers: String?,
        mappersIndexes: List<KotlinClassFileGenerator.MappersWithIndex>?,
        elementName: String
    ) = replace(Variable.NAME.value, screenName).replace(Variable.PACKAGE_NAME.value, packageName)
        .replace(Variable.PACKAGE_DIRECTORY.value, packageName.replace("package", "").trim())
        .replace(Variable.DATA_CLASS_PARAMS.value, getDataClassParamsWithoutAnnotations(fileBody))
        .replace(Variable.REMOTE_DATA_CLASS.value, fileBody).replace(Variable.MAPPERS_DECLARATION.value, mappers ?: "")
        .replace(
            Variable.MAPPER_DATA_CLASS_VARIABLES.value,
            getMapperBodyParams(getDataClassParamsWithoutAnnotations(fileBody), mappersIndexes, elementName)
        )

    private fun String.replaceVariableForFileName(
        screenName: String,
        packageName: String,
    ) = replace(Variable.NAME.value, screenName).replace(Variable.PACKAGE_NAME.value, packageName)
        .replace(Variable.PACKAGE_DIRECTORY.value, packageName.replace("package", "").trim())
}
