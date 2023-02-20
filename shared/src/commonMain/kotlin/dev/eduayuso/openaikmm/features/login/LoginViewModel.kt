package dev.eduayuso.openaikmm.features.login

import dev.eduayuso.openaikmm.domain.interactors.LoginUseCase
import dev.eduayuso.openaikmm.domain.interactors.type.Resource
import dev.eduayuso.openaikmm.presentation.IViewModel
import dev.eduayuso.openaikmm.presentation.ViewModel

interface ILoginViewModel:
    IViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>

open class LoginViewModel(

    private val loginUseCase: LoginUseCase,

) : ILoginViewModel, ViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun createInitialState() = LoginContract.State()

    override fun handleEvent(event: LoginContract.Event) {

        when(event) {
            is LoginContract.Event.ValidateForm -> validate(event.user)
            is LoginContract.Event.Authenticate -> authenticate(event.user)
        }
    }

    private fun validate(user: String): Boolean {

        val isUserValid = user.isNotEmpty()

        setState {
            copy(isValid = isUserValid)
        }
        return isUserValid
    }

    private fun authenticate(user: String) {

        collect(loginUseCase(user)) { result ->
            when(result) {
                is Resource.Success -> {
                    setEffect {
                        LoginContract.Effect.AuthSuccess
                    }
                }
                is Resource.Error -> {
                    setEffect {
                        LoginContract.Effect.AuthError(result.exception.message ?: "")
                    }
                }
            }
        }
    }
}