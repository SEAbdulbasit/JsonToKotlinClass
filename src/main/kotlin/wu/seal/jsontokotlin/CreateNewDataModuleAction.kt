/*
package wu.seal.jsontokotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.module.ModuleTypeId
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.ModuleRootModificationUtil
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import java.io.File
import java.io.IOException


*/
/**
 * Created by Seal.Wu on 2018/4/18.
 *//*

class CreateNewDataModuleAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return

        val dataContext = event.dataContext
        val module = LangDataKeys.MODULE.getData(dataContext) ?: return

        runWriteAction {
            customModuleSetup(project, "Abdul123", module)

        }

    }


    fun getOrCreateModule(project: Project, projectPath: String, moduleTypeId: String): Module {

        val moduleManager = ModuleManager.getInstance(project).modifiableModel
        val module = moduleManager.newModule(projectPath, moduleTypeId)

        val root = VfsUtil.findFileByIoFile(File(projectPath), true) ?: throw AssertionError("Can't find $projectPath")

        ModuleRootModificationUtil.updateModel(module) { t ->
            val e = t.addContentEntry(root)
            e.addSourceFolder(root, false)
        }

        moduleManager.commit()


        return module
    }


    fun customModuleSetup(
        project: Project, moduleName: String, module: Module
    ) {

        try {

            var f: VirtualFile = createProjectSubFile(
                ModuleRootManager.getInstance(module).contentRoots[0].path, "$moduleName/$moduleName.iml"
            )
            val module = ModuleManager.getInstance(project).newModule(f.path, ModuleTypeId.JAVA_MODULE)


            val model = ModuleRootManager.getInstance(module).modifiableModel
            val contentEntry: ContentEntry = model.addContentEntry(f.parent)
            //contentEntry.addSourceFolder(contentEntry.file?.url!!, false)
            contentEntry.addSourceFolder(contentEntry.file?.url!! + "/src", false)
            contentEntry.addSourceFolder(contentEntry.file?.url!!, false)
            model.commit()

            var directory = ModuleRootManager.getInstance(module).sourceRoots.asSequence().mapNotNull {
                PsiManager.getInstance(project).findDirectory(it)
            }.firstOrNull()

            directory?.createSubdirectory("src")?.createSubdirectory("main")//?.createSubdirectory("main1")
            //directory?.createSubdirectory("src2")?.createSubdirectory("main2")//?.createSubdirectory("main1")


        } catch (e: IOException) {
            throw RuntimeException(e)
        }


    }

    @Throws(IOException::class)
    fun createProjectSubFile(projectPath: String?, relativePath: String?): VirtualFile {
        val f = File(projectPath, relativePath)
        FileUtil.ensureExists(f.getParentFile())
        FileUtil.ensureCanCreateFile(f)
        val created: Boolean = f.createNewFile()
        if (!created && !f.exists()) {
            throw AssertionError("Unable to create the project sub file: " + f.getAbsolutePath())
        }
        return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(f)!!
    }


    fun String.save(srcDir: PsiDirectory, subDirPath: String, fileName: String) {
        try {
            val destDir = srcDir.createSubdirectory("test123")
            val psiFile = PsiFileFactory.getInstance(srcDir.project).createFileFromText(fileName, this)
            destDir.add(psiFile)
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }


}
*/
