package dev.eduayuso.openaikmm.android.navigation

sealed class Routes(val route: String) {

    object RouteIds {

        const val login = "login"
        const val chat = "chat"
        const val settings = "settings"
    }

    object Login: Routes(RouteIds.login)
    object Chat: Routes(RouteIds.chat)
    object Settings: Routes(RouteIds.settings)
}
