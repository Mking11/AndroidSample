package com.mking11.androidDemo.moduels.auth.features.login

import android.content.Context
import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthRepo
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.models.AppResult
import com.mking11.androidDemo.moduels.auth.features.login.util.handleException
import com.mking11.androidDemo.moduels.auth.features.login.util.loginInputValidation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class LoginByEmail(
    private val authRepo: FirebaseAuthRepo,
    private val firebaseCrash: FirebaseCrash
) {


    suspend operator fun invoke(
        userCredential: String,
        userPassword: String,
        context: Context,

        ): Flow<LoginState> = callbackFlow {
        this.trySend(LoginState()).isSuccess
        val isValid =
            loginInputValidation(userCredential, userPassword, onPassword = {
                this.trySend(LoginState(passwordError = it)).isSuccess
            }, onEmailError = {
                this.trySend(LoginState(userError = it)).isSuccess
            })
        if (isValid) {
            authRepo.userLogin(userCredential, userPassword).catch { e ->
                firebaseCrash.setErrorToFireBase(e, "invoke LoginUseCase.kt  29: ")
            }.collect {
                when (it) {
                    is AppResult.Error.NonRecoverableError -> {
                        it.exception?.let { e ->
                            this.trySend(
                                handleException(
                                    e, context
                                )
                            )
                        }
                    }
                    is AppResult.Error.RecoverableError -> {
                        it.exception?.let { e ->
                            this.trySend(
                                handleException(
                                    e, context
                                )
                            )
                        }
                    }
                    AppResult.InProgress -> {
                        this.trySend(LoginState(isOnProgress = true))
                    }
                    is AppResult.Success -> {
                        this.trySend(LoginState(isOnProgress = false, onSuccess = true))
                    }
                    else -> Unit
                }
            }
        }
        awaitClose { }
    }

}


