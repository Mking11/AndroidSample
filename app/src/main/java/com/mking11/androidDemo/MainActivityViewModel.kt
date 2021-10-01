package com.mking11.androidDemo

import androidx.lifecycle.ViewModel
import com.mking11.androidDemo.common.entites.user.IUserDaoRepository
import com.mking11.androidDemo.common.entites.user.models.UserDataDto
import com.mking11.androidDemo.common.utils.repo_utils.FirebaseRepoParent
import com.mking11.androidDemo.common.utils.repo_utils.RepoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userFirebaseRepository: FirebaseRepoParent<UserDataDto>,
    private val userDaoRepository: IUserDaoRepository
) : ViewModel() {


    val users = userDaoRepository.getUsers()

    init {
        userFirebaseRepository.observeValue(object : RepoCallback<UserDataDto> {
            override fun onAddedOrChanged(item: UserDataDto) {
                println("item ${item}")
                userDaoRepository.insertOrUpdateUser(item)
            }

            override fun onDeleted(item: UserDataDto) {
                userDaoRepository.deleteUser(item)
            }

        })
    }

}