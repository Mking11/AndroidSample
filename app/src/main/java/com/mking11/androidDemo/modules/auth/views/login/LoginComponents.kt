package com.mking11.androidDemo.modules.auth.views.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mking11.androidDemo.ui.components.OutlinedTextFieldWithError
import com.mking11.androidDemo.ui.components.PasswordOutlinedTextFieldWithError
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

    val error = loginViewModel.error

    Surface(modifier = Modifier.fillMaxSize()) {
        UserInputComposable(
            navController,
            loginViewModel.progress.value,
            error.value,
            scaffoldState,
            loginViewModel.passError.value,
            loginViewModel.userCredentialError.value
        ) { userCred, password ->
            loginViewModel.handleLogin(userCred, password) {
                onExitToMain()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun UserInputComposable(
    navController: NavController,
    progress: Boolean,
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
        verticalArrangement = Arrangement.Top,
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

        TextButton(
            {
                navController.navigate("signup_user")
            },
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Don't have an account signup ")
        }


    }


}
