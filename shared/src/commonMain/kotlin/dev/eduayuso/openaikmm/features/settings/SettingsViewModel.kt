package dev.eduayuso.openaikmm.features.settings

import dev.eduayuso.openaikmm.domain.entities.SettingsEntity
import dev.eduayuso.openaikmm.domain.interactors.GetSettingsUseCase
import dev.eduayuso.openaikmm.domain.interactors.SaveSettingsUseCase
import dev.eduayuso.openaikmm.domain.interactors.type.Resource
import dev.eduayuso.openaikmm.presentation.IViewModel
import dev.eduayuso.openaikmm.presentation.ViewModel

interface ISettingsViewModel:
    IViewModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>

open class SettingsViewModel(

    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase

) : ISettingsViewModel, ViewModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>() {

    override fun createInitialState() = SettingsContract.State()

    override fun handleEvent(event: SettingsContract.Event) {

        when(event) {
            is SettingsContract.Event.OnGetSettings -> getSettings()
            is SettingsContract.Event.UpdateUserField -> updateUserField(event.user)
            is SettingsContract.Event.UpdateModelSelection -> updateModelSelection(event.model)
            is SettingsContract.Event.SaveChanges -> saveSettings(event.user, event.model)
        }
    }

    private fun updateUserField(user: String) {

        validate(user)
        val currentSettings = state.value.settings
        setState {
            copy(settings = SettingsEntity(user = user, model = currentSettings.model))
        }
    }

    private fun updateModelSelection(model: String) {

        val currentSettings = state.value.settings
        setState {
            copy(settings = SettingsEntity(user = currentSettings.user, model = model))
        }
    }

    private fun validate(user: String): Boolean {

        val isUserValid = user.isNotEmpty()

        setState {
            copy(isUserValid = isUserValid)
        }
        return isUserValid
    }

    private fun getSettings() {

        collect(getSettingsUseCase()) { result ->
            when (result) {
                is Resource.Success -> {
                    setState {
                        copy(settings = result.data)
                    }
                }
                is Resource.Error -> setState {
                    copy(isError = true)
                }
            }
        }
    }

    private fun saveSettings(user: String, model: String) {

        val settings = SettingsEntity(user = user, model = model)
        collect(saveSettingsUseCase(settings)) { result ->
            when (result) {
                is Resource.Success -> {
                    setEffect {
                        SettingsContract.Effect.SettingsSaved
                    }
                }
                is Resource.Error -> setState {
                    copy(isError = true)
                }
            }
        }
    }
}