package filegenerator.data.util

fun String.toSnakeCase() = replace(Regex("([^_A-Z])([A-Z])"), "$1_$2").toLowerCase()
