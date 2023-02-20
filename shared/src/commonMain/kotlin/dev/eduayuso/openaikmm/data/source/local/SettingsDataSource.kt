package dev.eduayuso.openaikmm.data.source.local

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity

class SettingsDataSource {

    private val settings = SettingsEntity()

    fun saveUser(name: String) {

        this.settings.user = name
    }

    fun saveSettings(settings: SettingsEntity) {

        this.settings.apply {
            user = settings.user
            model = settings.model
        }
    }

    fun getSettings() = settings
}