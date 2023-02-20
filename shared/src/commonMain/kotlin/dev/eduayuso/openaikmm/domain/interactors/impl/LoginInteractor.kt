package dev.eduayuso.openaikmm.domain.interactors.impl

import dev.eduayuso.openaikmm.domain.interactors.LoginUseCase
import dev.eduayuso.openaikmm.domain.repository.ISettingsRepository

class LoginInteractor(

    private val repository: ISettingsRepository

): LoginUseCase() {

    override val block: suspend (param: String) -> Unit
        get() = {
            repository.saveUser(it)
        }
}