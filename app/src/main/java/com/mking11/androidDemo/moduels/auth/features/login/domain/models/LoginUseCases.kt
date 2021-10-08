package com.mking11.androidDemo.moduels.auth.features.login.domain.models

import com.mking11.androidDemo.moduels.auth.features.login.domain.use_cases.LoginByEmail
import com.mking11.androidDemo.moduels.auth.features.login.domain.use_cases.LoginByGoogle
import kotlinx.coroutines.ExperimentalCoroutinesApi
@ExperimentalCoroutinesApi
data class LoginUseCases (
    val loginByEmail: LoginByEmail,
    val loginByGoogle: LoginByGoogle
)