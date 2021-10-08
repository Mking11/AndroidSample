package com.mking11.androidDemo.moduels.auth.features.login

import com.google.firebase.auth.FirebaseAuth
import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthUtils
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.moduels.auth.features.login.domain.models.LoginUseCases
import com.mking11.androidDemo.moduels.auth.features.login.domain.use_cases.LoginByEmail
import com.mking11.androidDemo.moduels.auth.features.login.domain.use_cases.LoginByGoogle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {


    @Provides
    @ViewModelScoped
    @ExperimentalCoroutinesApi
    fun provideLoginUseCase(
        firebaseCrash: FirebaseCrash,
        firebaseAuthUtils: FirebaseAuthUtils,
        auth: FirebaseAuth
    ): LoginUseCases = LoginUseCases(
        loginByEmail = LoginByEmail(firebaseAuthUtils, firebaseCrash),
        loginByGoogle = LoginByGoogle(auth = auth)
    )
}