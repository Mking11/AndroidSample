package com.mking11.androidDemo.features.user.domain.use_cases

import com.mking11.androidDemo.features.user.UserRepository
import com.mking11.androidDemo.features.user.domain.model.UserDataDto

class InsertUsersToDb(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(users: List<UserDataDto>) {
        users.forEach { user -> userRepository.insertOrUpdateUserDb(user) }
    }
}