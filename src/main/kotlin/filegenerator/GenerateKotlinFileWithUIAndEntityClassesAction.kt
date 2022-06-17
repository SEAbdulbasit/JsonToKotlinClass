package filegenerator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.module.ModuleTypeId
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import filegenerator.data.file.FileCreator
import filegenerator.data.file.FileCreatorImpl
import filegenerator.data.file.ModuleFileCreatorImpl
import filegenerator.data.repository.SettingsRepositoryImpl
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.UnSupportJsonException
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.KotlinClassFileGenerator
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import java.io.File
import java.io.IOException


/**
 * Created by abdulbasit on 05/02/2022.
 */
class GenerateKotlinFileWithUIAndEntityClassesAction : AnAction("Kotlin Remote, UI and Entity Class File from JSON") {

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString = ""
        try {
            val project = event.getData(PlatformDataKeys.PROJECT) ?: return

            val settingsRepository = SettingsRepositoryImpl()
            val fileCreator = FileCreatorImpl(settingsRepository, project)

            val dataContext = event.dataContext
            val module = LangDataKeys.MODULE.getData(dataContext) ?: return


            val inputDialog = JsonInputDialog("", project)
            inputDialog.show()


            val className = inputDialog.getClassName()
            val packageName = inputDialog.getPackage()
            val isAndroidModule = inputDialog.getIsAndroidModuleFlag()

            val inputString = inputDialog.inputString.takeIf { it.isNotEmpty() } ?: return

            if (packageName.isNullOrEmpty().not()) {
                runWriteAction {
                    val moduleName = packageName.split(".").last()
                    val module = customModuleSetup(project, module, moduleName)

                    val moduleFilesCreator = ModuleFileCreatorImpl(settingsRepository, project)
                    var directory: PsiDirectory? =
                        ModuleRootManager.getInstance(module).sourceRoots.asSequence().mapNotNull {
                            PsiManager.getInstance(project).findDirectory(it)
                        }.firstOrNull() ?: return@runWriteAction

                    moduleFilesCreator.createModuleFiles("", directory!!, isAndroidModule, moduleName)

                    val subdirectory = fileCreator.findCodeSubdirectory("src.main.java", directory)!!

                    generateDirectoryAndCodeFiles(
                        subdirectory, fileCreator, packageName.replace("-", ""), inputString, className, project
                    )

                    val moduleDeclaration = directory.toString().split(project.name!!)[1].replace("\\", ":").replace("/", ":")
                    updateGradleSettingsAndAddTheModule(project, moduleDeclaration)

                }

            } else {

                var directory = when (val navigatable = LangDataKeys.NAVIGATABLE.getData(dataContext)) {
                    is PsiDirectory -> navigatable
                    is PsiFile -> navigatable.containingDirectory
                    else -> {
                        val root = ModuleRootManager.getInstance(module)
                        root.sourceRoots.asSequence().mapNotNull {
                            PsiManager.getInstance(project).findDirectory(it)
                        }.firstOrNull()
                    }
                } ?: return

                runWriteAction {
                    generateDirectoryAndCodeFiles(
                        directory, fileCreator, packageName, inputString, className, project
                    )
                }
            }


        } catch (e: UnSupportJsonException) {
            val advice = e.advice
            Messages.showInfoMessage(dealWithHtmlConvert(advice), "Tip")
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }

    private fun generateDirectoryAndCodeFiles(
        directory: PsiDirectory,
        fileCreator: FileCreatorImpl,
        packageName: String,
        inputString: String,
        className: String,
        project: Project
    ) {
        var directory1 = directory

        if (packageName.trim().isNotEmpty())
            directory1 = fileCreator.findCodeSubdirectory(packageName, directory1)!!

        val directoryFactory = PsiDirectoryFactory.getInstance(directory1.project)
        val packageName = directoryFactory.getQualifiedName(directory1, false)
        val packageDeclare = if (packageName.isNotEmpty()) "package $packageName" else ""

        doGenerateKotlinDataClassFileAction(
            className, inputString, packageDeclare.replace("src.main.java.",""), project, directory1, fileCreator
        )
    }

    private fun dealWithHtmlConvert(advice: String) = advice.replace("<", "&lt;").replace(">", "&gt;")

    private fun doGenerateKotlinDataClassFileAction(
        className: String,
        json: String,
        packageDeclare: String,
        project: Project?,
        directory: PsiDirectory,
        fileCreator: FileCreator
    ) {
        val kotlinClass = KotlinClassMaker(className, json).makeKotlinClass()
        val dataClassAfterApplyInterceptor =
            kotlinClass.applyInterceptors(InterceptorManager.getEnabledKotlinDataClassInterceptors())
        if (ConfigManager.isInnerClassModel) {

            KotlinClassFileGenerator().generateSingleKotlinClassFile(
                packageDeclare, dataClassAfterApplyInterceptor, project, directory, fileCreator
            )
        } else {
            KotlinClassFileGenerator().generateMultipleKotlinClassFiles(
                dataClassAfterApplyInterceptor, packageDeclare, project, directory, fileCreator
            )
        }
    }

    private fun customModuleSetup(
        project: Project,
        module: Module,
        moduleName: String,
    ): Module {

        try {

            var f: VirtualFile = createProjectSubFile(
                ModuleRootManager.getInstance(module).contentRoots[0].path,
                "$moduleName/$moduleName.iml"
            )
            val module = ModuleManager.getInstance(project).newModule(f.path, ModuleTypeId.JAVA_MODULE)

            val model = ModuleRootManager.getInstance(module).modifiableModel
            model.inheritSdk()
            val contentEntry: ContentEntry = model.addContentEntry(f.parent)
            contentEntry.addSourceFolder(contentEntry.file?.url!!, false)
            model.commit()

            return module


        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun createProjectSubFile(projectPath: String?, relativePath: String?): VirtualFile {
        val f = File(projectPath, relativePath)
        FileUtil.ensureExists(f.parentFile)
        FileUtil.ensureCanCreateFile(f)
        val created: Boolean = f.createNewFile()
        if (!created && !f.exists()) {
            throw AssertionError("Unable to create the project sub file: " + f.absolutePath)
        }
        return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(f)!!
    }

    private fun updateGradleSettingsAndAddTheModule(project: Project, moduleDeclaration: String) {
        val file = VfsUtil.findFileByIoFile(File(project.basePath + "/settings.gradle.kts"), true) ?: return

        if (file.fileType.isBinary) return  // file is binary

        val document = FileDocumentManager.getInstance().getDocument(file) ?: return

        CommandProcessor.getInstance().executeCommand(project, {
            ApplicationManager.getApplication().runWriteAction {
                val documentText = document.text.trim()
                if (documentText.endsWith("\n")) {
                    document.setText(documentText + "include(\"$moduleDeclaration\")")

                } else {
                    document.setText("$documentText\ninclude(\"$moduleDeclaration\")")
                }
            }
        }, "Update android manifest", null)
    }
}
