package dev.eduayuso.openaikmm.di

import dev.eduayuso.openaikmm.features.chat.ChatViewModel
import dev.eduayuso.openaikmm.features.login.LoginViewModel
import dev.eduayuso.openaikmm.features.settings.SettingsViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinViewModels: KoinComponent {

    val login: LoginViewModel by inject()
    val chat: ChatViewModel by inject()
    val settings: SettingsViewModel by inject()
}