package dev.eduayuso.openaikmm.domain.repository

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity

interface ISettingsRepository {

    fun saveUser(name: String)

    fun saveSettings(settings: SettingsEntity)

    fun getSettings(): SettingsEntity
}