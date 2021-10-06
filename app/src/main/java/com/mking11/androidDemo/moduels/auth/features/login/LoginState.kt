package com.mking11.androidDemo.moduels.auth.features.login

data class LoginState(
    val userError: String? = null,
    val passwordError: String? = null,
    val isOnProgress: Boolean = false,
    val isOnGoogleLogin: Boolean = false,
    val generalError: String? = null,
    val onSuccess: Boolean = false
)