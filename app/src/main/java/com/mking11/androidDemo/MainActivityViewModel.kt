package com.mking11.androidDemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.utils.repo_utils.RepoCallback
import com.mking11.androidDemo.features.user.UserRepository
import com.mking11.androidDemo.features.user.domain.model.UserDataDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseCrash: FirebaseCrash
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " MainActivityViewModel.kt  21: ")
    }


    private val userDataCallbacks = object : RepoCallback<UserDataDto> {
        override fun onAddedOrChanged(item: UserDataDto) {
            viewModelScope.launch(handler) {
                userRepository.insertOrUpdateUserDb(item)
            }
        }

        override fun onDeleted(item: UserDataDto) {
            viewModelScope.launch(handler) {
                userRepository.deleteUserDb(item)
            }
        }

    }

    init {
        viewModelScope.launch {
            userRepository.getUsersDb()?.collect {

                println("users ${it}")
            }

        }


        userRepository.observeUser(userDataCallbacks)
    }

    override fun onCleared() {
        userRepository.closeRepository()
        super.onCleared()
    }
}