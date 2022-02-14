package filegenerator.model

fun defaultScreenElements() = mutableListOf(

    ScreenElement(
        name = "Entity",
        template = FileType.ENTITY_DATA_CLASS.bodyTemplate,
        fileType = FileType.ENTITY_DATA_CLASS,
        fileNameTemplate = FileType.ENTITY_DATA_CLASS.fileNameTemplate,
        subdirectory = "entity/model"
    ), ScreenElement(
        name = "EntityMapper",
        template = FileType.ENTITY_MAPPER.bodyTemplate,
        fileType = FileType.ENTITY_MAPPER,
        fileNameTemplate = FileType.ENTITY_MAPPER.fileNameTemplate,
        subdirectory = "entity/mapper"
    ), ScreenElement(
        name = "UI",
        template = FileType.UI_DATA_CLASS.bodyTemplate,
        fileType = FileType.UI_DATA_CLASS,
        fileNameTemplate = FileType.UI_DATA_CLASS.fileNameTemplate,
        subdirectory = "ui/model"
    ), ScreenElement(
        name = "UiMapper",
        template = FileType.UI_MAPPER.bodyTemplate,
        fileType = FileType.UI_MAPPER,
        fileNameTemplate = FileType.UI_MAPPER.fileNameTemplate,
        subdirectory = "ui/mapper"
    ), ScreenElement(
        name = "Remote",
        template = FileType.REMOTE_DATA_CLASS.bodyTemplate,
        fileType = FileType.REMOTE_DATA_CLASS,
        fileNameTemplate = FileType.REMOTE_DATA_CLASS.fileNameTemplate,
        subdirectory = "remote"
    )
)

fun moduleElements() = mutableListOf(
    ScreenElement(
        name = "",
        template = FileType.GIT_IGNORE.bodyTemplate,
        fileType = FileType.GIT_IGNORE,
        fileNameTemplate = FileType.GIT_IGNORE.fileNameTemplate,
        subdirectory = ""
    ),
    ScreenElement(
        name = "build.gradle.kts",
        template = FileType.BUILD_GRADLE.bodyTemplate,
        fileType = FileType.BUILD_GRADLE,
        fileNameTemplate = FileType.BUILD_GRADLE.fileNameTemplate,
        subdirectory = ""
    ),
    )


