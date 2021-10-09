package com.mking11.androidDemo.moduels.auth.features.login.domain.use_cases

import android.content.Context
import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthUtils
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.models.AppResult
import com.mking11.androidDemo.moduels.auth.features.login.domain.models.LoginState
import com.mking11.androidDemo.moduels.auth.features.login.domain.utils.handleException
import com.mking11.androidDemo.moduels.auth.features.login.domain.utils.loginInputValidation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class LoginByEmail(
    private val authUtils: FirebaseAuthUtils,
    private val firebaseCrash: FirebaseCrash
) {

    // the reason this was done by mutable state is cause the errors might be multiple at a time for the validation
    suspend operator fun invoke(
        userCredential: String,
        userPassword: String,
        context: Context
    ): Flow<LoginState> = callbackFlow {

        val isValid = validateInput(userCredential, userPassword)
        if (isValid.first) {
            authUtils.userLogin(userCredential, userPassword).catch { e ->
                firebaseCrash.setErrorToFireBase(e, "invoke LoginUseCase.kt  29: ")
            }.collect {
                when (it) {
                    is AppResult.Error.NonRecoverableError -> {
                        it.exception?.let { e ->
                            trySend(
                                isValid.second.handleException(
                                    e, context
                                )
                            ).isSuccess
                        }

                    }
                    is AppResult.Error.RecoverableError -> {
                        it.exception?.let { e ->
                            trySend(isValid.second.handleException(e, context)).isSuccess
                        }
                    }
                    AppResult.InProgress -> {
                        trySend(isValid.second.copy(isOnProgress = true)).isSuccess
                    }
                    is AppResult.Success -> {
                        trySend(
                            isValid.second.copy(
                                isOnProgress = false,
                                onSuccess = true
                            )
                        ).isSuccess
                    }
                    else -> Unit
                }
            }
        } else {
            trySend(isValid.second).isSuccess
        }

        awaitClose {  }

    }

    private fun validateInput(
        userCredential: String,
        userPassword: String,
    ): Pair<Boolean, LoginState> {
        var loginState = LoginState()
        val result: Boolean = loginInputValidation(userCredential, userPassword, onPassword = {
            loginState = loginState.copy(passwordError = it)
        }, onEmailError = {
            loginState = loginState.copy(userError = it)
        })

        return Pair(result, loginState)
    }


}