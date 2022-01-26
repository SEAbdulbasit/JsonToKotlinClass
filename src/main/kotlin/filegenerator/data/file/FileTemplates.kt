package filegenerator.data.file

import filegenerator.model.Variable

val ENTITY_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.entity.model\n\n" +
            "import io.swvl.basemodels.EntityModel\n" +
            "\n\ndata class ${Variable.NAME.value}Entity(${Variable.DATA_CLASS_PARAMS.value}) : EntityModel"

val UI_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.ui.model\n\n" +
            "import io.swvl.basemodels.UiModel\n\n" +
            "data class ${Variable.NAME.value}UiModel(${Variable.DATA_CLASS_PARAMS.value}) : UiModel"

val REMOTE_DATA_CLASS_TEMPLATE = "" +
        "${Variable.PACKAGE_NAME.value}.remote.model\n\n" +
        "import com.squareup.moshi.Json\n" +
        "import com.squareup.moshi.JsonClass\n" +
        "import io.swvl.basemodels.RemoteModel\n\n" +
        "${Variable.REMOTE_DATA_CLASS.value} : RemoteModel"

val ENTITY_MAPPER_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.entity.mapper\n\n" +
            "import io.swvl.basemodels.EntityMapper\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.remote.model.${Variable.NAME.value}Remote\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.entity.model.${Variable.NAME.value}Entity\n\n" +
            "class ${Variable.NAME.value}EntityMapper : EntityMapper<${Variable.NAME.value}Remote, ${Variable.NAME.value}Entity> {\n" +
            "    override fun mapFromItem(model: ${Variable.NAME.value}Entity): ${Variable.NAME.value}Remote {\n" +
            "        throw UnsupportedOperationException()\n" +
            "    }\n" +
            "\n" +
            "    override fun mapToItem(model: ${Variable.NAME.value}Remote): ${Variable.NAME.value}Entity {\n" +
            "        return with(model) {\n" +
            "            ${Variable.NAME.value}Entity(${Variable.MAPPER_DATA_CLASS_VARIABLES.value}" +
            "            )\n" +
            "        }\n" +
            "    }\n" +
            "}"

val UI_MAPPER_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.ui.mapper\n\n" +
            "import io.swvl.basemodels.UiMapper\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.entity.model.${Variable.NAME.value}Entity\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.ui.model.${Variable.NAME.value}UiModel\n\n" +

            "class ${Variable.NAME.value}UiMapper : UiMapper<${Variable.NAME.value}Entity, ${Variable.NAME.value}UiModel> {\n" +
            "    override fun mapFromUiModel(model: ${Variable.NAME.value}UiModel): ${Variable.NAME.value}Entity {\n" +
            "        throw UnsupportedOperationException()\n" +
            "    }\n" +
            "\n" +
            "    override fun mapToUiModel(model: ${Variable.NAME.value}Entity): ${Variable.NAME.value}UiModel {\n" +
            "        return with(model) {\n" +
            "            ${Variable.NAME.value}UiModel(${Variable.MAPPER_DATA_CLASS_VARIABLES.value}" +
            "            )\n" +
            "        }\n" +
            "    }\n" +
            "}"


fun getMapperBodyParams(value: String): String {
    val trimmedStrings = value.replace(Regex("\n|\t"), "").replace(Regex("val |var"), "")
        .replace(Regex("[ ?]"), "")
        .replace(Regex(":([A-z]*)"), "")
        .replace(Regex(": ([A-z]*)"), "")

    val splitStrings = trimmedStrings.split(",")
    var mappedStrings = ""

    splitStrings.forEachIndexed() { index, variableName ->
        mappedStrings = if (index < (splitStrings.size - 1))
            "$mappedStrings$variableName = $variableName,"
        else
            "$mappedStrings$variableName = $variableName"
    }

    return mappedStrings
}

fun getDataClassParamsWithoutAnnotations(value: String): String {
    return value
        .replace(Regex("(@[A-z]*)+\\([^)]*\\)"), "")
        .replace(Regex("([data]+( [a-zA-Z]+)+)"), "")
        .replace(Regex("([data]+( [a-zA-Z]+)+)"), "")
        .replace(Regex("\\("), "")
        .replace(Regex("\\)"), "").trim().replace("\n","").replace("\t","")
}

fun updateRemoteDataClassName(value: String): String {
    val regex = "([data]+( [a-zA-Z]+)+)".toRegex()
    val className = regex.find(value)?.value + "Remote"
    return value.replace(regex, className)

}

