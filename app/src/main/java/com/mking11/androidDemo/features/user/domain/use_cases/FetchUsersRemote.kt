package com.mking11.androidDemo.features.user.domain.use_cases

import com.mking11.androidDemo.features.user.UserRepository
import com.mking11.androidDemo.features.user.domain.model.UserDataDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class FetchUsersRemote(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<UserDataDto>> {
        return userRepository.fetchUserRemote().map {
            it.values.toList()
        }
    }
}