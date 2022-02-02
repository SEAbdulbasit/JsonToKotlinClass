package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import wu.seal.jsontokotlin.filetype.KotlinFileType

interface Directory {
    fun findSubdirectory(name: String): Directory?
    fun createSubdirectory(name: String): Directory
    fun addFile(file: File)
}

class DirectoryImpl(
    private val project: Project,
    private val psiDirectory: PsiDirectory
) : Directory {

    override fun findSubdirectory(name: String) =
        psiDirectory.findSubdirectory(name)?.let { DirectoryImpl(project, it) }

    override fun createSubdirectory(name: String) = DirectoryImpl(project, psiDirectory.createSubdirectory(name))

    override fun addFile(file: File) {
        val psiFile = PsiFileFactory.getInstance(project)
            .createFileFromText("${file.name}.${file.fileType.extension}", KotlinFileType(), file.content)

        //format the code
        val styleManager = CodeStyleManager.getInstance(project)
        val formattedPsiFile = styleManager.reformat(psiFile)

        psiDirectory.add(formattedPsiFile)


    }
}
