package filegenerator.data.repository

import filegenerator.model.ScreenElement
import filegenerator.model.androidModuleElements
import filegenerator.model.defaultModuleElements
import filegenerator.model.moduleElements

/**
 * Created by abdulbasit on 03/02/2022.
 */

interface SettingsRepository {
    fun loadScreenElements(): List<ScreenElement>
    fun loadModuleElements(): List<ScreenElement>
    fun loadAndroidModuleElements(): List<ScreenElement>
}

class SettingsRepositoryImpl : SettingsRepository {
    override fun loadScreenElements() = defaultModuleElements()
    override fun loadModuleElements() = moduleElements()
    override fun loadAndroidModuleElements() = androidModuleElements()

}