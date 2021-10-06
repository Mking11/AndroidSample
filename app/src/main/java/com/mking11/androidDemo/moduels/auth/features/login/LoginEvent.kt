package com.mking11.androidDemo.moduels.auth.features.login

import android.content.Context

sealed class LoginEvent {
    data class LoginEmail(val userCredential: String, val password: String, val context: Context) : LoginEvent()
}