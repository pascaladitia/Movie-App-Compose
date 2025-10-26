@file:OptIn(
    ExperimentalMotionApi::class,
    ExperimentalSharedTransitionApi::class
)

package com.pascal.movie.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavController
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSharedTransitionApi::class)
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
        SharedTransitionLayout {
            val sharedScope: SharedTransitionScope = this

            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ) {
                composable(route = Screen.SplashScreen.route) {
                    SplashScreen(
                        paddingValues = paddingValues
                    ) {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
                composable(route = Screen.HomeScreen.route) {
                    val animScope: AnimatedVisibilityScope = this
                    HomeScreen(
                        paddingValues = paddingValues,
                        sharedTransitionScope = sharedScope,
                        animatedVisibilityScope = animScope,
                        onDetail = {
                            saveToCurrentBackStack(navController, "movies", it)
                            navController.navigate(Screen.DetailScreen.route)
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
                    val animScope: AnimatedVisibilityScope = this
                    DetailScreen(
                        paddingValues = paddingValues,
                        sharedTransitionScope = sharedScope,
                        animatedVisibilityScope = animScope,
                        moviesResponse = getFromPreviousBackStack(navController, "movies"),
                        onNavBack = {
                            navController.navigateUp()
                        }
                    )
                }
                composable(route = Screen.ProfileScreen.route) {
                    ProfileScreen(
                        paddingValues = paddingValues,
                        onDetail = {

                        }
                    )
                }
            }
        }
    }
}

inline fun <reified T> saveToCurrentBackStack(
    navController: NavController,
    key: String,
    data: T
) {
    val json = Json.encodeToString(data)
    navController.currentBackStackEntry?.savedStateHandle?.set(key, json)
}

inline fun <reified T> getFromPreviousBackStack(
    navController: NavController,
    key: String
): T? {
    val json = navController.previousBackStackEntry?.savedStateHandle?.get<String>(key)
    return json?.let { Json.decodeFromString(it) }
}
