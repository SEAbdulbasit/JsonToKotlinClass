package filegenerator.model

enum class Variable(val value: String, val description: String) {
    NAME("%screenName%", "Name of the screen, e.g. ScreenName"),
    PACKAGE_NAME("%packageName%", "Full package name, e.g. com.sample"),
    PACKAGE_DIRECTORY("%packageDirectory%", "Full package name, e.g. com.sample"),
    DATA_CLASS_PARAMS("%dataClassVariables%", "Data class params"),
    REMOTE_DATA_CLASS("%remoteDataClass%", "Remote Data class params"),
    MAPPER_TO_ITEM_DATA_CLASS_VARIABLES("%mapperToItemDataClassVariables%", "Mapper Data class variables"),
    MAPPER_FROM_ITEM_DATA_CLASS_VARIABLES("%mapperFromItemDataClassVariables%", "Mapper Data class variables"),
    KOTLIN_GENERATED_CODE("KKKOTLIN_GENERATED_CODEKKK", "Mapper Data class variables"),
    MAPPERS_DECLARATION("%mappersDeclaration%", "Mapper declaration"),
}