package filegenerator.data.repository

import filegenerator.model.ScreenElement
import filegenerator.model.defaultScreenElements
import filegenerator.model.moduleElements

interface SettingsRepository {
    fun loadScreenElements(): List<ScreenElement>
    fun loadModuleElements(): List<ScreenElement>
}

class SettingsRepositoryImpl : SettingsRepository {

    override fun loadScreenElements() = defaultScreenElements()
    override fun loadModuleElements() = moduleElements()

}