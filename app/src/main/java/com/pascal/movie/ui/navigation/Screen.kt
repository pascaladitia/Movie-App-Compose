package com.pascal.movie.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash")
    data object HomeScreen: Screen("home")
    data object TeamScreen: Screen("team")
    data object LiveScreen: Screen("live")
    data object ProfileScreen: Screen("profile")
}