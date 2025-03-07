package filegenerator.model

import filegenerator.data.file.*

/**
 * Created by abdulbasit on 05/02/2022.
 */

private val KOTLIN_DEFAULT_FILE_NAME = "${Variable.NAME.value}"
private val UI_MAPPER_NAME = "${Variable.NAME.value}UiMapper"
private val ENTITY_MAPPER_NAME = "${Variable.NAME.value}EntityMapper"
private val GIT_IGNORE_NAME = ""
private val GRADLE_NAME_KTS = "build.gradle"
private val MANIFEST_NAME = "AndroidManifest"


enum class FileType(
    val displayName: String,
    val extension: String,
    val bodyTemplate: String,
    val fileNameTemplate: String = KOTLIN_DEFAULT_FILE_NAME

) {
    ENTITY_DATA_CLASS("Entity", "kt", ENTITY_DATA_CLASS_TEMPLATE),
    REMOTE_DATA_CLASS("Remote", "kt", REMOTE_DATA_CLASS_TEMPLATE),
    UI_DATA_CLASS("UiModel", "kt", UI_DATA_CLASS_TEMPLATE),
    ENTITY_MAPPER("", "kt", ENTITY_MAPPER_DATA_CLASS_TEMPLATE, ENTITY_MAPPER_NAME),
    UI_MAPPER("", "kt", UI_MAPPER_DATA_CLASS_TEMPLATE, UI_MAPPER_NAME),
    GIT_IGNORE("", "gitignore", GIT_IGNORE_TEMPLATE, GIT_IGNORE_NAME),
    BUILD_GRADLE("build.gradle", "kts", GRADLE_KTS, GRADLE_NAME_KTS),
    ANDROID_BUILD_GRADLE("build.gradle", "kts", ANDROID_GRADLE_KTS, GRADLE_NAME_KTS),
    ANDROID_XML("AndroidManifest", "xml", ANDROID_MANIFEST, MANIFEST_NAME);

    override fun toString() = displayName
}