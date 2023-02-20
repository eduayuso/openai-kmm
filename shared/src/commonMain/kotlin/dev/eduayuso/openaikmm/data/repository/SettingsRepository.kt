package dev.eduayuso.openaikmm.data.repository

import dev.eduayuso.openaikmm.data.source.local.SettingsDataSource
import dev.eduayuso.openaikmm.domain.entities.OpenAIModels
import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.domain.repository.ISettingsRepository

class SettingsRepository(

    private val data: SettingsDataSource

): ISettingsRepository {

    override fun saveUser(name: String) {

        if (name.isEmpty()) {
            throw Exception("Please, enter your name")
        } else {
            data.saveUser(name)
        }
    }

    override fun saveSettings(settings: SettingsEntity) {

        data.saveSettings(settings)
    }

    override fun getSettings() = data.getSettings()
}