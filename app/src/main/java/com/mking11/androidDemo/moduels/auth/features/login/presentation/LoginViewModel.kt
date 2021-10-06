package com.mking11.androidDemo.moduels.auth.features.login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.moduels.auth.features.login.LoginEvent
import com.mking11.androidDemo.moduels.auth.features.login.LoginState
import com.mking11.androidDemo.moduels.auth.features.login.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class LoginViewModel
@Inject constructor(
    private val firebaseCrash: FirebaseCrash,
    private val loginUseCases: LoginUseCases
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " LoginViewModel.kt  19: ")
    }

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState


    @ExperimentalCoroutinesApi
    fun loginEvents(loginEvent: LoginEvent) = viewModelScope.launch {
        when (loginEvent) {
            is LoginEvent.LoginEmail -> {
                loginUseCases.loginByEmail.invoke(
                    loginEvent.userCredential,
                    loginEvent.password,
                    loginEvent.context,
                ).catch { e ->
                    firebaseCrash.setErrorToFireBase(e, "loginEvents LoginViewModel.kt  46: ")
                }.collect {
                    println("State changed ${it}")
                    _loginState.value = it
                }
            }
        }
    }


}