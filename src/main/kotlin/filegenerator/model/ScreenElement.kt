package filegenerator.model

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
        dataClassParamsWithoutAnnotations: String,
        mappersDeclaration: String,
        mappersParams: String,
    ) = template.replaceVariables(
        screenName, packageName, fileBody, dataClassParamsWithoutAnnotations, mappersDeclaration, mappersParams
    )

    fun fileName(
        screenName: String,
    ) = fileNameTemplate.replaceVariableForFileName(screenName).run {
        this
    }

    private fun String.replaceVariables(
        screenName: String,
        packageName: String,
        fileBody: String,
        dataClassParamsWithoutAnnotations: String,
        mappersDeclaration: String,
        mappersParams: String,
    ) = replace(Variable.NAME.value, screenName).replace(Variable.PACKAGE_NAME.value, packageName)
        .replace(Variable.PACKAGE_DIRECTORY.value, packageName.replace("package", "").trim())
        .replace(Variable.DATA_CLASS_PARAMS.value, dataClassParamsWithoutAnnotations)
        .replace(Variable.REMOTE_DATA_CLASS.value, fileBody)
        .replace(Variable.MAPPERS_DECLARATION.value, mappersDeclaration)
        .replace(Variable.MAPPER_DATA_CLASS_VARIABLES.value, mappersParams)

    private fun String.replaceVariableForFileName(
        screenName: String,
    ) = replace(Variable.NAME.value, screenName)
}
