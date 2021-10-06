package com.mking11.androidDemo.moduels.auth.features.login

import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthRepo
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
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
        firebaseAuthRepo: FirebaseAuthRepo
    ): LoginUseCases = LoginUseCases(
        loginByEmail = LoginByEmail(authRepo = firebaseAuthRepo, firebaseCrash)
    )
}