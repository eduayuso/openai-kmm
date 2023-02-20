package dev.eduayuso.openaikmm.domain.interactors.impl

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.domain.interactors.SaveSettingsUseCase
import dev.eduayuso.openaikmm.domain.repository.ISettingsRepository

class SaveSettingsInteractor(

    private val repository: ISettingsRepository

): SaveSettingsUseCase() {

    override val block: suspend (param: SettingsEntity) -> Unit
        get() = {
            repository.saveSettings(it)
        }
}