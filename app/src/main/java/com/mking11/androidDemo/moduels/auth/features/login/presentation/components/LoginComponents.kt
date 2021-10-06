package com.mking11.androidDemo.moduels.auth.features.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.mking11.androidDemo.R
import com.mking11.androidDemo.common.presentation.components.OutlinedTextFieldWithError
import com.mking11.androidDemo.common.presentation.components.PasswordOutlinedTextFieldWithError
import com.mking11.androidDemo.moduels.auth.features.login.LoginEvent
import com.mking11.androidDemo.moduels.auth.features.login.presentation.components.SignInButton
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun LoginComponent(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onExitToMain: () -> Unit,
) {

    val state = loginViewModel.loginState.value
    val context = LocalContext.current


    if (state.onSuccess) {
        onExitToMain()
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        UserInputComposable(
            navController,
            state.isOnProgress,
            state.isOnGoogleLogin,
            state.generalError,
            scaffoldState,
            state.passwordError,
            state.userError
        ) { userCred, password ->
            loginViewModel.loginEvents(LoginEvent.LoginEmail(userCred, password, context))
        }
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun UserInputComposable(
    navController: NavController,
    progress: Boolean,
    googleSignIn: Boolean,
    error: String?,
    scaffoldState: ScaffoldState,
    passError: String?,
    userError: String?,
    handleCheck: (String, String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    if (error != null) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(error)
        }
    }

    val userName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextFieldWithError(
            fieldValue = userName.value,
            onValueChanged = { result ->
                userName.value = result
            },
            onError = userError != null,
            errorValue = userError ?: "Error",
            fieldLabel = "Email",
            type = KeyboardType.Email,
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        )


        PasswordOutlinedTextFieldWithError(
            fieldValue = password.value,
            onValueChanged = { result ->
                password.value = result
            },
            fieldLabel = "Password",
            onError = passError != null,
            errorValue = passError ?: "Error",
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        )


        if (progress) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            )
        }


        Button(
            {
                handleCheck(userName.value, password.value)
                keyboardController?.hide()
            },
            modifier = Modifier
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(), enabled = !progress
        ) {
            Text("Login")
        }


        SignInButton(
            modifier = Modifier.fillMaxWidth(0.8f).padding(top = 20.dp, start = 20.dp, end = 20.dp),
            text = stringResource(R.string.sign_in_with_google),
            loadingText = stringResource(R.string.sigining_in),
            icon = rememberImagePainter(R.drawable.common_google_signin_btn_icon_light_normal),
            isLoading = googleSignIn
        ) {

        }



    }


}
