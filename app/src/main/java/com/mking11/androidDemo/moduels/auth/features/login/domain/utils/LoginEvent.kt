package com.mking11.androidDemo.moduels.auth.features.login.domain.utils

import android.content.Context
import androidx.activity.result.ActivityResult

sealed class LoginEvent {
    data class LoginEmail(val userCredential: String, val password: String, val context: Context) :
        LoginEvent()

    data class LoginGoogle(val errorMessage: String, val result: ActivityResult) : LoginEvent()
}