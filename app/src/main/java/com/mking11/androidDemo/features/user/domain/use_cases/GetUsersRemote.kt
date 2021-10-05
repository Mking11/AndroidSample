package com.mking11.androidDemo.features.user.domain.use_cases

import com.mking11.androidDemo.features.user.UserRepository
import com.mking11.androidDemo.features.user.domain.model.UserDataDto
import kotlinx.coroutines.flow.Flow


class GetUsersRemote(
    private val userRepository: UserRepository
) {

//    operator fun invoke(): Flow<HashMap<String, UserDataDto>> {
//
//    }
}