package filegenerator.model

import filegenerator.data.file.getDataClassParamsWithoutAnnotations
import filegenerator.data.file.getMapperBodyParams
import filegenerator.data.file.updateRemoteDataClassName
import filegenerator.data.util.toSnakeCase
import java.io.Serializable

private const val UNNAMED_ELEMENT = "UnnamedElement"
const val DEFAULT_SOURCE_SET = "main"

data class ScreenElement(
    var name: String = "",
    var template: String = "",
    var fileType: FileType,
    var fileNameTemplate: String = "",
    var relatedAndroidComponent: AndroidComponent = AndroidComponent.NONE,
    var categoryId: Int = 0,
    var subdirectory: String = "",
    var sourceSet: String = DEFAULT_SOURCE_SET
) : Serializable {

    override fun toString() = name

    fun body(
        screenName: String,
        packageName: String,
        androidComponent: String,
        params: String
    ) =
        template
            .replaceVariables(screenName, packageName, androidComponent, params)

    fun fileName(
        screenName: String,
        packageName: String,
        androidComponent: String,
    ) =
        fileNameTemplate
            .replaceVariables(screenName, packageName, androidComponent, "")
            .run {
                this
            }

    private fun String.replaceVariables(
        screenName: String,
        packageName: String,
        androidComponent: String,
        params: String
    ) =
        replace(Variable.NAME.value, screenName)
            .replace(Variable.NAME_SNAKE_CASE.value, screenName.toSnakeCase())
            .replace(Variable.NAME_LOWER_CASE.value, screenName.decapitalize())
            .replace(Variable.SCREEN_ELEMENT.value, name)
            .replace(Variable.PACKAGE_NAME.value, packageName)
            .replace(Variable.PACKAGE_NAME.value, packageName)
            .replace(Variable.PACKAGE_DIRECTORY.value, packageName.replace("package", "").trim())
            .replace(Variable.DATA_CLASS_PARAMS.value, getDataClassParamsWithoutAnnotations(params))
            .replace(Variable.REMOTE_DATA_CLASS.value, updateRemoteDataClassName(params))
            .replace(
                Variable.MAPPER_DATA_CLASS_VARIABLES.value,
                getMapperBodyParams(getDataClassParamsWithoutAnnotations(params))
            )
            .replace(Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value, androidComponent.decapitalize())

}
