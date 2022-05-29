package com.example.linklibrary.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.linklibrary.screens.SplashScreen
import com.example.linklibrary.screens.WebViewScreen
import com.example.linklibrary.screens.action_choice.ActionChoice
import com.example.linklibrary.screens.detail.DetailScreen
import com.example.linklibrary.screens.home.HomeScreen
import com.example.linklibrary.screens.info_screen.InfoScreen
import com.example.linklibrary.screens.login.LoginScreen
import com.example.linklibrary.screens.update.UpdateScreen
import com.example.ssjetpackcomposeswipeableview.SwipeDirection


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.name
    ) {

        composable(AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(AppScreens.ActionChoice.name) {
            ActionChoice(navController = navController)
        }
        composable(AppScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(AppScreens.UpdateScreen.name) {
            UpdateScreen(navController = navController)
        }
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController, swipeDirection = SwipeDirection.BOTH,
            )
        }
        composable(AppScreens.WebViewScreen.name) {
            WebViewScreen(navController = navController)
        }
        composable(AppScreens.InfoScreen.name) {
            InfoScreen(navController = navController)
        }
        val detailName = AppScreens.DetailScreen.name
        composable("$detailName/{title}", arguments = listOf(navArgument("title") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("title").let {

                DetailScreen(navController = navController, swipeDirection = SwipeDirection.BOTH)
            }
        }
    }
}