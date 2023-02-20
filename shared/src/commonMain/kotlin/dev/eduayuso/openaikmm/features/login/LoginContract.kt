package dev.eduayuso.openaikmm.features.login

import dev.eduayuso.openaikmm.domain.entities.MessageEntity
import dev.eduayuso.openaikmm.presentation.UIEffect
import dev.eduayuso.openaikmm.presentation.UIEvent
import dev.eduayuso.openaikmm.presentation.UIState

interface LoginContract {

    data class State(

        val name: String? = null,
        val isValid: Boolean? = null

    ): UIState

    sealed interface Event: UIEvent {

        data class ValidateForm(val user: String): Event
        data class Authenticate(val user: String): Event
    }

    sealed interface Effect: UIEffect {

        object AuthSuccess: Effect
        data class AuthError(val message: String): Effect
    }
}