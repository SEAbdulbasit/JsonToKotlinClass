package filegenerator.model

private val KOTLIN_DEFAULT_TEMPLATE =
    "package ${Variable.PACKAGE_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
private const val LAYOUT_XML_DEFAULT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
        "    android:layout_width=\"match_parent\"\n" +
        "    android:layout_height=\"match_parent\">\n" +
        "\n" +
        "</FrameLayout>"
private val KOTLIN_DEFAULT_FILE_NAME = "${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"

//private val LAYOUT_XML_DEFAULT_FILE_NAME =
//    "${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}"
private val LAYOUT_XML_DEFAULT_FILE_NAME =
    "AndroidManifest"


enum class FileType(
    val displayName: String,
    val extension: String,
    val defaultTemplate: String,
    val defaultFileName: String
) {
    KOTLIN("Kotlin", "kt", KOTLIN_DEFAULT_TEMPLATE, KOTLIN_DEFAULT_FILE_NAME),
    LAYOUT_XML("Layout XML", "xml", LAYOUT_XML_DEFAULT_TEMPLATE, LAYOUT_XML_DEFAULT_FILE_NAME),
    DATA_CLASS("Kotlin", "kt", KOTLIN_DEFAULT_TEMPLATE, KOTLIN_DEFAULT_FILE_NAME),

    MAPPER_CLASS("Kotlin", "kt", KOTLIN_DEFAULT_TEMPLATE, KOTLIN_DEFAULT_FILE_NAME),

    BUILD_GRADLE("Build Gradle", "kts", KOTLIN_DEFAULT_TEMPLATE, "build.gradle"),
    GIT_IGNORE("Git Ignore", "gitignore", KOTLIN_DEFAULT_TEMPLATE, "");

    override fun toString() = displayName
}