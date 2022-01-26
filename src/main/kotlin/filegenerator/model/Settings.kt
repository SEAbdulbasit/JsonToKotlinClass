package filegenerator.model

import filegenerator.data.file.*
import java.io.Serializable

private fun defaultScreenElements() = mutableListOf(

    ScreenElement(
        "Entity",
        ENTITY_DATA_CLASS_TEMPLATE,
        FileType.DATA_CLASS,
        FileType.DATA_CLASS.defaultFileName,
        subdirectory = "entity/model"
    ),
    ScreenElement(
        "EntityMapper",
        ENTITY_MAPPER_DATA_CLASS_TEMPLATE,
        FileType.MAPPER_CLASS,
        FileType.MAPPER_CLASS.defaultFileName,
        subdirectory = "entity/mapper"
    ),

    ScreenElement(
        "UI",
       UI_DATA_CLASS_TEMPLATE,
        FileType.DATA_CLASS,
        FileType.DATA_CLASS.defaultFileName,
        subdirectory = "ui/model"
    ),
    ScreenElement(
        "UIMapper",
        UI_MAPPER_DATA_CLASS_TEMPLATE,
        FileType.MAPPER_CLASS,
        FileType.MAPPER_CLASS.defaultFileName,
        subdirectory = "ui/mapper"
    ),

    ScreenElement(
        "Remote",
        REMOTE_DATA_CLASS_TEMPLATE,
        FileType.DATA_CLASS,
        FileType.DATA_CLASS.defaultFileName,
        subdirectory = "remote/model"
    ),


//
//    ScreenElement(
//        "Android Manifest",
//        DEFAULT_REPOSITORY_TEMPLATE,
//        FileType.LAYOUT_XML,
//        FileType.LAYOUT_XML.defaultFileName,
//        subdirectory = "src/main"
//    ),
//
//    ScreenElement(
//        "Build Gradle",
//        DEFAULT_ACTIVITY_TEMPLATE,
//        FileType.BUILD_GRADLE,
//        FileType.BUILD_GRADLE.defaultFileName
//    ),
//    ScreenElement("Git Ignore", DEFAULT_ACTIVITY_TEMPLATE, FileType.GIT_IGNORE, FileType.GIT_IGNORE.defaultFileName),
)

private fun defaultCategories() = mutableListOf(
    Category(0, "Default")
)

data class Settings(
    var screenElements: MutableList<ScreenElement> = defaultScreenElements(),
    var categories: MutableList<Category> = defaultCategories()
) : Serializable