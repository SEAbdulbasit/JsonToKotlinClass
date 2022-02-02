package wu.seal.jsontokotlin.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.file.FileCreator
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

class KotlinClassFileGenerator {

    fun generateSingleKotlinClassFile(
        packageDeclare: String,
        kotlinClass: KotlinClass,
        project: Project?,
        directory: PsiDirectory,
        fileCreator: FileCreator
    ) {
        val fileNamesWithoutSuffix = currentDirExistsFileNamesWithoutKTSuffix(directory)
        var kotlinClassForGenerateFile = kotlinClass
        while (fileNamesWithoutSuffix.contains(kotlinClass.name)) {
            kotlinClassForGenerateFile =
                kotlinClassForGenerateFile.rename(newName = kotlinClassForGenerateFile.name + "X")
        }

        fileCreator.createScreenFiles(
            splitDataClass = kotlinClass,
            packageName = packageDeclare,
            psiDirectory = directory,
        )

        val notifyMessage = "Kotlin Data Class file generated successful"
        showNotify(notifyMessage, project)
    }

    fun generateMultipleKotlinClassFiles(
        kotlinClass: KotlinClass,
        packageDeclare: String,
        project: Project?,
        directory: PsiDirectory,
        fileCreator: FileCreator
    ) {
        val fileNamesWithoutSuffix = currentDirExistsFileNamesWithoutKTSuffix(directory)
        val existsKotlinFileNames = IgnoreCaseStringSet().also { it.addAll(fileNamesWithoutSuffix) }
        val splitClasses =
            kotlinClass.resolveNameConflicts(existsKotlinFileNames).getAllModifiableClassesRecursivelyIncludeSelf()

        splitClasses.forEach { splitDataClass ->
            fileCreator.createScreenFiles(
                splitDataClass = splitDataClass,
                packageName = packageDeclare,
                psiDirectory = directory,
            )
            val notifyMessage = buildString {
                append("${splitClasses.size} Kotlin Data Class files generated successful")

            }
            showNotify(notifyMessage, project)
        }
    }

    private fun currentDirExistsFileNamesWithoutKTSuffix(directory: PsiDirectory): List<String> {
        val kotlinFileSuffix = ".kt"
        return directory.files.filter { it.name.endsWith(kotlinFileSuffix) }
            .map { it.name.dropLast(kotlinFileSuffix.length) }
    }

}

