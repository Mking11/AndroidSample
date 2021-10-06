package com.mking11.androidDemo.moduels.auth.features.login

import kotlinx.coroutines.ExperimentalCoroutinesApi
@ExperimentalCoroutinesApi
data class LoginUseCases (
    val loginByEmail: LoginByEmail
)