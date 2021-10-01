package com.mking11.androidDemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mking11.androidDemo.common.entites.user.IUserDaoRepository
import com.mking11.androidDemo.common.entites.user.models.UserDataDto
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.utils.repo_utils.FirebaseRepoParent
import com.mking11.androidDemo.common.utils.repo_utils.RepoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userFirebaseRepository: FirebaseRepoParent<UserDataDto>,
    private val userDaoRepository: IUserDaoRepository,
    private val firebaseCrash: FirebaseCrash
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e ->
        firebaseCrash.setErrorToFireBase(e, " MainActivityViewModel.kt  21: ")
    }
    val users = userDaoRepository.getUsers()

    private val userDataCallbacks = object : RepoCallback<UserDataDto> {
        override fun onAddedOrChanged(item: UserDataDto) {
            viewModelScope.launch(handler) {
                userDaoRepository.insertOrUpdateUser(item)
            }
        }

        override fun onDeleted(item: UserDataDto) {
            viewModelScope.launch(handler) {
                userDaoRepository.deleteUser(item)
            }
        }

    }

    init {
        userFirebaseRepository.observeValue(userDataCallbacks)
    }

}