package wu.seal.jsontokotlin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import javax.swing.text.JTextComponent

/**
 * Dialog widget relative
 * Created by Seal.wu on 2017/9/21.
 */

/**
 * Json input Dialog
 */
private val jsonInputDialogValidator: JsonInputDialogValidator = JsonInputDialogValidator()

class PackageNameInputDialog(classsName: String, private val project: Project) : Messages.InputDialog(
    project,
    "Please input the JSON String and class name to generate Kotlin data class",
    "Generate Kotlin Data Class Code",
    null,
    "",
    jsonInputDialogValidator
) {
    private lateinit var packageNameInput: JTextComponent

    init {
        setOKButtonText("Generate")
        okAction.isEnabled = true
        myField.text = classsName
    }


    /**
     * get the user input class name
     */
    fun getClassName(): String {
        return if (exitCode == 0) {
            val name = myField.text.trim()
            name.let { if (it.first().isDigit() || it.contains('$')) "`$it`" else it }
        } else ""
    }


}
