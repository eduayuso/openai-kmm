package dev.eduayuso.openaikmm.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.eduayuso.openaikmm.android.features.chat.ChatView
import dev.eduayuso.openaikmm.android.features.login.LoginView
import dev.eduayuso.openaikmm.android.features.settings.SettingsView
import dev.eduayuso.openaikmm.di.KoinViewModels
import dev.eduayuso.openaikmm.features.chat.ChatContract
import dev.eduayuso.openaikmm.features.settings.SettingsContract

@Composable
fun NavigationGraph(
    viewModels: KoinViewModels
) {

    val navController = rememberNavController()
    val startDestination = Routes.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Routes.Login.route) {
            LoginView(navController, viewModels.login)
        }
        composable(Routes.Chat.route) {
            ChatView(navController, viewModels.chat)
            val initEvent = ChatContract.Event.OnGetSettings
            viewModels.chat.setEvent(initEvent)
        }
        composable(Routes.Settings.route) {
            SettingsView(navController, viewModels.settings)
            val initEvent = SettingsContract.Event.OnGetSettings
            viewModels.settings.setEvent(initEvent)
        }
    }
}