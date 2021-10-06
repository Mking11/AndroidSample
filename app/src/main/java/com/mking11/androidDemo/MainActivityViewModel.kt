package com.mking11.androidDemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.features.user.domain.model.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val firebaseCrash: FirebaseCrash
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " MainActivityViewModel.kt  21: ")
    }


    init {
        userUseCases.observeRemote.invoke()
        viewModelScope.launch(handler) {
            userUseCases.fetchRemote.invoke().collect {
                userUseCases.insertUsersToDb.invoke(it)
            }
        }
    }

    override fun onCleared() {
        userUseCases.closeRepository.invoke()
        super.onCleared()
    }
}