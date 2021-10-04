package com.mking11.androidDemo.modules.auth.views.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthRepo
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.models.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: FirebaseAuthRepo,
    private val firebaseCrash: FirebaseCrash
) : ViewModel() {


    private val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " LoginViewModel.kt  19: ")
    }

    val userCredentialError = mutableStateOf<String?>(null)
    val passError = mutableStateOf<String?>(null)
    val error = mutableStateOf<String?>(null)
    val progress = mutableStateOf(false)


    fun clearErrors() {
        userCredentialError.value = null
        passError.value = null
        error.value = null
    }


    @ExperimentalCoroutinesApi
    fun handleLogin(userCredential: String, userPassword: String, onSuccess: () -> Unit) =
        viewModelScope.launch {
            clearErrors()
            val isValid = loginInputValidation(userCredential, userPassword, onPassword = {
                passError.value = it
            }, onEmailError = {
                userCredentialError.value = it
            })

            if (isValid) {
                authRepo.userLogin(userCredential, userPassword).catch { e ->
                    firebaseCrash.setErrorToFireBase(e, "handleLogin LoginViewModel.kt  49: ")
                }.collect {
                    when (it) {
                        is AppResult.Error.NonRecoverableError -> {
                            progress.value = false
                            error.value = it.exception?.message ?: "Error"
                        }
                        is AppResult.Error.RecoverableError -> {
                            progress.value = false
                            error.value = it.exception?.message ?: "Error"
                        }
                        is AppResult.Failure -> {
                            progress.value = false
                            error.value = it.messages
                        }
                        AppResult.InProgress -> {
                            progress.value = true
                        }
                        is AppResult.Success -> {
                            progress.value = false
                            onSuccess
                        }
                    }
                }
            }


        }
}