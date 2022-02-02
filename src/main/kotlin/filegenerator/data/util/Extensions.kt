package filegenerator.data.util

fun String.toSnakeCase() = replace(Regex("([^_A-Z])([A-Z])"), "$1_$2").toLowerCase()

fun String.toCamelCase(): String {
    val words: List<String> = this.split("[\\W_]+")

    val builder = StringBuilder()
    for (i in words.indices) {
        var word: String = words.get(i)
        word = if (i == 0) {
            if (word.isEmpty()) word else word.toLowerCase()
        } else {
            if (word.isEmpty()) word else Character.toUpperCase(word[0]).toString() + word.substring(1).toLowerCase()
        }
        builder.append(word)
    }
    return builder.toString()
}


fun getParamVariableNameFromList(objectName: String): String {
    val regex = "([a-zA-Z]+(_[a-zA-Z]+)+)".toRegex()
    return regex.find(objectName)?.value ?: objectName
}
