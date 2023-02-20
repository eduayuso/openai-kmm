package dev.eduayuso.openaikmm.domain.interactors.impl

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.domain.interactors.GetSettingsUseCase
import dev.eduayuso.openaikmm.domain.repository.ISettingsRepository

class GetSettingsInteractor(

    private val repository: ISettingsRepository

): GetSettingsUseCase() {

    override val block: suspend () -> SettingsEntity
        get() = {
            repository.getSettings()
        }
}