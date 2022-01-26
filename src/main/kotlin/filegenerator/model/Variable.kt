package filegenerator.model

enum class Variable(val value: String, val description: String) {
    NAME("%screenName%", "Name of the screen, e.g. ScreenName"),
    NAME_SNAKE_CASE("%screenNameSnakeCase%", "Name of the screen written in snake case, e.g. screen_name"),
    NAME_LOWER_CASE(
        "%screenNameLowerCase%",
        "Name of the screen written in camel case starting with lower case, e.g. screenName"
    ),
    SCREEN_ELEMENT("%screenElement%", "Screen element's name, e.g. Presenter"),
    PACKAGE_NAME("%packageName%", "Full package name, e.g. com.sample"),
    PACKAGE_DIRECTORY("%packageDirectory%", "Full package name, e.g. com.sample"),
    ANDROID_COMPONENT_NAME_LOWER_CASE(
        "%componentLowerCase%",
        "Android component's name written in camel case starting with lower case, e.g. activity or fragment"
    ),
    DATA_CLASS_PARAMS("%dataClassVariables%", "Data class params"),
    REMOTE_DATA_CLASS("%remoteDataClass%", "Remote Data class params"),
    MAPPER_DATA_CLASS_VARIABLES("%mapperDataClassVariables%", "Mapper Data class variables"),
}