package filegenerator.data.repository

import filegenerator.model.ScreenElement
import filegenerator.model.defaultScreenElements

interface SettingsRepository {
    fun loadScreenElements(): List<ScreenElement>
}

class SettingsRepositoryImpl : SettingsRepository {

    override fun loadScreenElements() = defaultScreenElements()

}