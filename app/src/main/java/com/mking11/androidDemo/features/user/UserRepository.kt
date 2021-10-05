package com.mking11.androidDemo.features.user

import com.mking11.androidDemo.features.user.domain.model.UserDataDbo
import com.mking11.androidDemo.features.user.domain.model.UserDataDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertOrUpdateUserDb(userDataDbo: UserDataDto)
    suspend fun deleteUserDb(userDataDbo: UserDataDto)
    fun getUsersDb(): Flow<List<UserDataDbo>>?
    fun getUserRemote(): Flow<HashMap<String, UserDataDto>>
    fun closeRepository()
}