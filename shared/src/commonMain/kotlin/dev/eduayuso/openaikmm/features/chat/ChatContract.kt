package dev.eduayuso.openaikmm.features.chat

import dev.eduayuso.openaikmm.domain.entities.MessageEntity
import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.presentation.UIEffect
import dev.eduayuso.openaikmm.presentation.UIEvent
import dev.eduayuso.openaikmm.presentation.UIState

interface ChatContract {

    data class State(

        val settings: SettingsEntity? = null,
        val messageList: List<MessageEntity>? = null,
        val isError: Boolean = false

    ): UIState

    sealed interface Event: UIEvent {

        object OnGetSettings: Event
        data class OnSendMessage(val text: String): Event
        data class OnReceiveMessage(val text: String): Event
        object OnSettingsTapped: Event
        object OnLogout: Event
    }

    sealed interface Effect: UIEffect {

        object NavigateToLogin: Effect
        object NavigateToSettings: Effect
    }
}