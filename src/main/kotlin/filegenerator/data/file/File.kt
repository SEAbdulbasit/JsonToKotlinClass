package filegenerator.data.file

import filegenerator.model.FileType

/**
 * Created by abdulbasit on 01/02/2022.
 */

data class File(
    val name: String,
    val content: String,
    val fileType: FileType
)