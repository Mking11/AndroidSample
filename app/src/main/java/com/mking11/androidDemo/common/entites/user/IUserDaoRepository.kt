package com.mking11.androidDemo.common.entites.user

import androidx.lifecycle.LiveData
import com.mking11.androidDemo.common.entites.user.dao.UserDao
import com.mking11.androidDemo.common.entites.user.models.UserDataDbo
import com.mking11.androidDemo.common.entites.user.models.UserDataDto
import com.mking11.androidDemo.common.utils.dao_utils.DaoRepo

interface IUserDaoRepository {
    suspend fun insertOrUpdateUser(userDataDbo: UserDataDto)
    suspend  fun deleteUser(userDataDbo: UserDataDto)
    fun getUsers(): LiveData<List<UserDataDbo>>?
}