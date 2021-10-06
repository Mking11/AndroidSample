package com.mking11.androidDemo.features.user.domain.use_cases

import com.mking11.androidDemo.features.user.UserRepository

class CloseRepositories(private val userRepository: UserRepository) {
    operator fun invoke() {
        userRepository.closeRemote()
    }
}