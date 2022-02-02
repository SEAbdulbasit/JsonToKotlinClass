package filegenerator.data.file

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import filegenerator.data.repository.SettingsRepository
import filegenerator.model.ScreenElement
import wu.seal.jsontokotlin.utils.KotlinClassFileGenerator

interface FileCreator {

    fun createScreenFiles(
        mappers: List<KotlinClassFileGenerator.MappersWithIndex>?,
        packageName: String,
        screenName: String,
        psiDirectory: PsiDirectory,
        fileBody: String,
    )
}

class FileCreatorImpl constructor(
    private val settingsRepository: SettingsRepository, private val project: Project
) : FileCreator {

    override fun createScreenFiles(
        mappers: List<KotlinClassFileGenerator.MappersWithIndex>?,
        packageName: String,
        screenName: String,
        psiDirectory: PsiDirectory,
        fileBody: String,
    ) {
        val regex = "KKK/?.*?KKK".toRegex()

        settingsRepository.loadScreenElements().forEach {
            var file = File(
                name = it.fileName(
                    screenName = getScreenName(regex, screenName, it), packageName = packageName
                ), content = it.body(
                    screenName = getScreenName(regex, screenName, it),
                    packageName = packageName,
                    fileBody = regex.replace(fileBody, it.fileType.displayName),
                    mappers = getMappersDeclarations(regex, mappers, it),
                    mappersIndexes = mappers,
                    elementName = it.name
                ), fileType = it.fileType
            )

            if (psiDirectory != null) {
                addFile(psiDirectory, file, it.subdirectory.toLowerCase())
            }
        }

    }

    private fun getMappersDeclarations(
        regex: Regex, mappers: List<KotlinClassFileGenerator.MappersWithIndex>?, it: ScreenElement
    ) = regex.replace(getMappersDeclaration(mappers, it, regex), it.fileType.displayName)

    private fun getScreenName(
        regex: Regex, screenName: String, it: ScreenElement
    ) = regex.replace(screenName, it.fileType.displayName)

    private fun getMappersDeclaration(
        mappers: List<KotlinClassFileGenerator.MappersWithIndex>?, screenElement: ScreenElement, regex: Regex
    ): String {
        var mappersValue = "\n"
        mappers?.forEach {
            val mapper = regex.replace(it.typeObjectName, screenElement.fileType.displayName)
            mappersValue += "val ${toCamelCase(mapper)}${screenElement.name} by lazy { ${it.typeObjectName}${screenElement.name}() }\n"

            it.mapperVariableName = "${toCamelCase(mapper)}${screenElement.name}"
        }
        return mappersValue
    }

    fun toCamelCase(text: String): String {
        val words: List<String> = text.split("[\\W_]+")

        val builder = StringBuilder()
        for (i in words.indices) {
            var word: String = words.get(i)
            word = if (i == 0) {
                if (word.isEmpty()) word else word.toLowerCase()
            } else {
                if (word.isEmpty()) word else Character.toUpperCase(word[0]).toString() + word.substring(1)
                    .toLowerCase()
            }
            builder.append(word)
        }
        return builder.toString()
    }


    private fun addFile(directory: PsiDirectory, file: File, subdirectory: String) {
        if (subdirectory.isNotEmpty()) {
            var newSubdirectory = directory
            subdirectory.split("/").forEach { segment ->
                newSubdirectory =
                    newSubdirectory.findSubdirectory(segment) ?: newSubdirectory.createSubdirectory(segment)
            }
            DirectoryImpl(project, newSubdirectory).addFile(file)
        } else {
            DirectoryImpl(project, directory).addFile(file)
        }
    }


    fun findCodeSubdirectory(packageName: String, directory: PsiDirectory): PsiDirectory? {
        var subdirectory = directory
        packageName.split(".").forEach {
            subdirectory = subdirectory.findSubdirectory(it) ?: subdirectory.createSubdirectory(it)
        }
        return subdirectory
    }
}
