package filegenerator.data.file

import filegenerator.model.Variable

val ENTITY_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.entity.model\n\n" + "import io.swvl.basemodels.EntityModel\n" + "\n\ndata class ${Variable.NAME.value}(${Variable.DATA_CLASS_PARAMS.value}) : EntityModel"

val UI_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.ui.model\n\n" + "import io.swvl.basemodels.UiModel\n\n" + "data class ${Variable.NAME.value}(${Variable.DATA_CLASS_PARAMS.value}) : UiModel"

val REMOTE_DATA_CLASS_TEMPLATE =
    "" + "${Variable.PACKAGE_NAME.value}.remote\n\n" + "import com.squareup.moshi.Json\n" + "import com.squareup.moshi.JsonClass\n" + "import io.swvl.basemodels.RemoteModel\n\n" + "${Variable.REMOTE_DATA_CLASS.value} : RemoteModel"

val ENTITY_MAPPER_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.entity.mapper\n\n" +
            "import io.swvl.basemodels.EntityMapper\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.remote.${Variable.NAME.value}Remote\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.entity.model.${Variable.NAME.value}Entity\n\n" +
            "class ${Variable.NAME.value}EntityMapper : EntityMapper<${Variable.NAME.value}Remote, ${Variable.NAME.value}Entity> {\n" +
            "   ${Variable.MAPPERS_DECLARATION.value}" +
            "   override fun mapFromItem(model: ${Variable.NAME.value}Entity): ${Variable.NAME.value}Remote {\n" +
            "       return with(model) {\n" +
            "           ${Variable.NAME.value}Remote(${Variable.MAPPER_FROM_ITEM_DATA_CLASS_VARIABLES.value}" + ")\n" +
            "       }\n" +
            "   }\n" +
            "   override fun mapToItem(model: ${Variable.NAME.value}Remote): ${Variable.NAME.value}Entity {\n" +
            "       return with(model) {\n" +
            "           ${Variable.NAME.value}Entity(${Variable.MAPPER_TO_ITEM_DATA_CLASS_VARIABLES.value}" + ")\n" +
            "       }\n" +
            "   }\n" + "}"

val UI_MAPPER_DATA_CLASS_TEMPLATE =
    "${Variable.PACKAGE_NAME.value}.ui.mapper\n\n" +
            "import io.swvl.basemodels.UiMapper\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.ui.model.${Variable.NAME.value}UiModel\n" +
            "import ${Variable.PACKAGE_DIRECTORY.value}.entity.model.${Variable.NAME.value}Entity\n\n" +
            "class ${Variable.NAME.value}UiMapper : UiMapper<${Variable.NAME.value}Entity, ${Variable.NAME.value}UiModel> {\n" +
            "   ${Variable.MAPPERS_DECLARATION.value}" +
            "   override fun mapFromUiModel(model: ${Variable.NAME.value}UiModel): ${Variable.NAME.value}Entity {\n" +
            "       return with(model) {\n" +
            "           ${Variable.NAME.value}Entity(${Variable.MAPPER_FROM_ITEM_DATA_CLASS_VARIABLES.value}" + ")\n" +
            "       }\n" +
            "   }\n" +
            "   override fun mapToUiModel(model: ${Variable.NAME.value}Entity): ${Variable.NAME.value}UiModel {\n" +
            "       return with(model) {\n" +
            "           ${Variable.NAME.value}UiModel(${Variable.MAPPER_TO_ITEM_DATA_CLASS_VARIABLES.value}" + ")\n" +
            "       }\n" +
            "   }\n" + "}"

