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
            "   ${Variable.MAPPERS_DECLARATION.value}\n" +
            "    override fun mapFromItem(model: ${Variable.NAME.value}Entity): ${Variable.NAME.value}Remote {\n" +
            "       return with(model) {\n" +
            "           ${Variable.NAME.value}Remote(${Variable.MAPPER_FROM_ITEM_DATA_CLASS_VARIABLES.value}" + ")\n" +
            "       }\n" +
            "   }\n" +
            "    override fun mapToItem(model: ${Variable.NAME.value}Remote): ${Variable.NAME.value}Entity {\n" +
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
            "   ${Variable.MAPPERS_DECLARATION.value}\n" +
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

val GIT_IGNORE_TEMPLATE = "/build"
val GRADLE_KTS = "plugins {\n" +
        "    id(\"java-library\")\n" +
        "    id(\"kotlin\")\n" +
        "    id(\"kotlin-kapt\")\n" +
        "}\n" +
        "\n" +
        "dependencies {\n" +
        "    implementation(Libs.STDLIB)\n" +
        "    api(Libs.MOSHI)\n" +
        "    api(project(Libs.Projects.LIBS_BASE_MODELS))\n" +
        "    kapt(Libs.MOSHI_KOTLIN_CODE_GEN)\n" +
        "}\n" +
        "\n" +
        "java {\n" +
        "    sourceCompatibility = JavaVersion.VERSION_1_7\n" +
        "    targetCompatibility = JavaVersion.VERSION_1_7\n" +
        "}"

val ANDROID_GRADLE_KTS = "plugins {\n" +
        "    id(\"com.android.library\")\n" +
        "    id(\"kotlin-android\")\n" +
        "    id(\"kotlin-android-extensions\")\n" +
        "    id(\"kotlin-kapt\")\n" +
        "}\n" +
        "\n" +
        "android {\n" +
        "    compileSdkVersion(Versions.COMPILE_SDK)\n" +
        "\n" +
        "    defaultConfig {\n" +
        "        minSdkVersion(Versions.MIN_SDK)\n" +
        "        targetSdkVersion(Versions.TARGET_SDK)\n" +
        "        versionCode = Versions.VERSION_CODE\n" +
        "        versionName = Versions.VERSION_NAME\n" +
        "        multiDexEnabled = true\n" +
        "    }\n" +
        "\n" +
        "    buildTypes {\n" +
        "        getByName(\"release\") {\n" +
        "            isMinifyEnabled = false\n" +
        "            proguardFiles(getDefaultProguardFile(\"proguard-android.txt\"), \"proguard-rules.pro\")\n" +
        "        }\n" +
        "    }\n" +
        "}\n" +
        "\n" +
        "dependencies {\n" +
        "    implementation(Libs.STDLIB)\n" +
        "    api(Libs.MOSHI)\n" +
        "    api(project(Libs.Projects.LIBS_BASE_MODELS))\n" +
        "    kapt(Libs.MOSHI_KOTLIN_CODE_GEN)\n" +
        "    kapt(Libs.MOSHI_KOTLIN_CODE_GEN)\n" +
        "}\n" +
        "\n" +
        "java {\n" +
        "    sourceCompatibility = JavaVersion.VERSION_1_7\n" +
        "    targetCompatibility = JavaVersion.VERSION_1_7\n" +
        "}"
val ANDROID_MANIFEST = "<manifest package=\"io.swvl.${Variable.MODULE_NAME.value}\" />\n"
