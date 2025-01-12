package com.pascal.movie.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pascal.movie.ui.screen.detail.DetailScreen
import com.pascal.movie.ui.screen.favorite.FavoriteScreen
import com.pascal.movie.ui.screen.home.HomeScreen
import com.pascal.movie.ui.screen.profile.ProfileScreen
import com.pascal.movie.ui.screen.splash.SplashScreen

@Composable
fun RouteScreen(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.HomeScreen.route,
                    Screen.FavoriteScreen.route,
                    Screen.ProfileScreen.route
                )) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
        ) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen(
                    paddingValues = paddingValues
                ) {
                    navController.navigate( Screen.HomeScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(
                    paddingValues = paddingValues,
                    onDetail = {
                        navController.navigate(Screen.DetailScreen.createRoute(it))
                    }
                )
            }
            composable(route = Screen.FavoriteScreen.route) {
                FavoriteScreen(
                    paddingValues = paddingValues,
                    onDetail = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = Screen.DetailScreen.route) {
                DetailScreen(
                    paddingValues = paddingValues,
                    id = it.arguments?.getString("id") ?: "",
                    onNavBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
