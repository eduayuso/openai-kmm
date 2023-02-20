package dev.eduayuso.openaikmm.features.settings

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.features.login.LoginContract
import dev.eduayuso.openaikmm.presentation.UIEffect
import dev.eduayuso.openaikmm.presentation.UIEvent
import dev.eduayuso.openaikmm.presentation.UIState

interface SettingsContract {

    data class State(

        val settings: SettingsEntity = SettingsEntity(),
        val isUserValid: Boolean = true,
        val isError: Boolean? = null

    ): UIState

    sealed interface Event: UIEvent {

        object OnGetSettings: Event
        data class UpdateUserField(val user: String): Event
        data class UpdateModelSelection(val model: String): Event
        data class SaveChanges(val user: String, val model: String): Event
    }

    sealed interface Effect: UIEffect {

        object SettingsSaved: Effect
    }
}