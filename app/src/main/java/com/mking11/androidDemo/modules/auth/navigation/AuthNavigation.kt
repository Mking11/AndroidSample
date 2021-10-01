package com.mking11.androidDemo.modules.auth.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@ExperimentalComposeUiApi
@Composable
fun AuthNavigation(scaffoldState: ScaffoldState, handleNavigateOut: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.LOGIN_SCREEN) {
        composable(Screens.LOGIN_SCREEN) {

        }
    }
}