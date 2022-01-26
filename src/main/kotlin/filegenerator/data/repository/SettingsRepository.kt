package filegenerator.data.repository

import filegenerator.model.ScreenElement
import filegenerator.model.Settings

interface SettingsRepository {
    fun loadScreenElements(): List<ScreenElement>
}

class SettingsRepositoryImpl : SettingsRepository {

    override fun loadScreenElements() = Settings().screenElements

}