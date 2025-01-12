package com.pascal.movie.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash")
    data object HomeScreen: Screen("home")
    data object FavoriteScreen: Screen("favorite")
    data object ProfileScreen: Screen("profile")
    data object DetailScreen: Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}