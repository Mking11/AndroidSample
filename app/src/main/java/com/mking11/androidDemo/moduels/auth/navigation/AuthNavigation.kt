package com.mking11.androidDemo.moduels.auth.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mking11.androidDemo.moduels.auth.features.login.presentation.components.LoginComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun AuthNavigation(
    scaffoldState: ScaffoldState,
    navigateToMain: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.LOGIN_SCREEN) {
        composable(Screens.LOGIN_SCREEN) {

            LoginComponent(navController, scaffoldState = scaffoldState) {
                navigateToMain()
            }
        }
    }
}