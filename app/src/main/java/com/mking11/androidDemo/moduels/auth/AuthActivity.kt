package com.mking11.androidDemo.moduels.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.mking11.androidDemo.MainActivity
import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthUtils
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.moduels.auth.navigation.AuthNavigation
import com.mking11.androidDemo.ui.theme.AndriodTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class AuthActivity : ComponentActivity() {


    @Inject
    lateinit var firebaseCrash: FirebaseCrash

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var firebaseAuthUtils: FirebaseAuthUtils


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (firebaseAuthUtils.isSingedIn()) {
            handleNavigateOut()
        }
        setContent {
            AndriodTheme {

                val scaffoldState = rememberScaffoldState()
                // A surface container using the 'background' color from the theme
                Scaffold(scaffoldState = scaffoldState, snackbarHost = {
                    SnackbarHost(hostState = it) { data ->
                        Snackbar(
                            modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary),
                            snackbarData = data
                        )
                    }
                }) {
                    AuthNavigation(scaffoldState) {
                        handleNavigateOut()
                    }
                }
            }
        }
    }


    private fun handleNavigateOut() {
        try {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(e, "handleNavigateOut AuthActivity.kt  109: ")
        }

    }
}
