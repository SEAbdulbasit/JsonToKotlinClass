package filegenerator.data.file

import filegenerator.data.util.getParamVariableNameFromList
import filegenerator.data.util.toCamelCase
import filegenerator.model.ScreenElement
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

class GenerateFilesFromTemplates(val kotlinClass: KotlinClass) {

    val regex = "KKK/?.*?KKK".toRegex()
    private val mapperList = mutableListOf<MappersWithIndex>()

    fun getFileName(screenNameElement: ScreenElement) =
        regex.replace(kotlinClass.name, screenNameElement.fileType.displayName)

    fun fileBodyWithRegexApplied(screenNameElement: ScreenElement) =
        regex.replace(kotlinClass.getOnlyCurrentCode(), screenNameElement.fileType.displayName)

    fun getDataClassParamsWithOutAnnotations(regexAppliedFileBody: String): String {
        return regexAppliedFileBody.replace(Regex("(@[A-z]*)+\\([^)]*\\)"), "")
            .replace(Regex("([data]+( [a-zA-Z]+)+)"), "").replace(Regex("\\("), "").replace(Regex("\\)"), "").trim()
            .replace("\n", "").replace("\t", "")

    }

    fun mappersDeclaration(screenElement: ScreenElement): String {
        var mappersDeclarationString = ""
        mapperList.map {
            mappersDeclarationString += "val ${it.name.toCamelCase()}${screenElement.name} by lazy { ${it.name}${screenElement.name}() }\n"
        }
        return mappersDeclarationString
    }

    fun getMappersParams(fileBodyClass: String, screenNameElement: ScreenElement): String {
        val getVariableNames =
            fileBodyClass.replace(Regex("\n|\t"), "").replace(Regex("val |var"), "").replace(Regex("[ ?]"), "")
                .replace(Regex(":([A-z]*)"), "").replace(Regex(": ([A-z]*)"), "").replace(Regex("<[a-zA-Z]+>"), "")

        val splitStrings = getVariableNames.split(",")
        var mappedStrings = ""

        splitStrings.forEachIndexed { index, variableName ->
            val mapper = mapperList.find { it.index == index }
            if (mapper != null) {
                when (mapper.typeObject) {
                    is DataClass -> {

                        mappedStrings += "$variableName = ${mapper.name.toCamelCase()}${screenNameElement.name}.mapToItem($variableName)"
                    }
                    is GenericListClass -> {
                        mappedStrings += "$variableName =$variableName.map{ ${mapper.name.toCamelCase()}${screenNameElement.name}.mapToItem(it)}"
                    }
                    else -> {
                        mappedStrings += "$variableName =${mapper.name.toCamelCase()}${screenNameElement.name}.mapToItem($variableName)"
                    }
                }

            } else {
                mappedStrings += "$variableName = $variableName"
            }

            if (index != splitStrings.size - 1) mappedStrings += ","
        }

        return mappedStrings


    }

    fun setMappers(screenNameElement: ScreenElement) {
        mapperList.clear()
        if (kotlinClass is DataClass) {
            kotlinClass.properties.forEachIndexed { index, property ->
                when (property.typeObject) {
                    is DataClass -> {
                        mapperList.add(
                            MappersWithIndex(
                                index = index, name = regex.replace(
                                    property.typeObject.name, screenNameElement.fileType.displayName
                                ), typeObject = DataClass(name = property.name)
                            )
                        )
                    }
                    is GenericListClass -> {
                        mapperList.add(
                            MappersWithIndex(
                                index = index, name = regex.replace(
                                    getParamVariableNameFromList(property.typeObject.name),
                                    screenNameElement.fileType.displayName
                                ), typeObject = GenericListClass(generic = DataClass(name = property.name))
                            )
                        )
                    }
                }
            }
        }
    }

    data class MappersWithIndex(
        val index: Int, val name: String, var typeObject: KotlinClass
    )

}