package filegenerator.data.file

import filegenerator.model.FileType

data class File(
    val name: String,
    val content: String,
    val fileType: FileType
)