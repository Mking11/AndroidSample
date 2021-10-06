package com.mking11.androidDemo.features.user.domain.model

import com.mking11.androidDemo.features.user.domain.use_cases.CloseRepositories
import com.mking11.androidDemo.features.user.domain.use_cases.FetchUsersRemote
import com.mking11.androidDemo.features.user.domain.use_cases.InsertUsersToDb
import com.mking11.androidDemo.features.user.domain.use_cases.ObserveRemote

data class UserUseCases(
    val fetchRemote: FetchUsersRemote,
    val insertUsersToDb: InsertUsersToDb,
    val observeRemote: ObserveRemote,
    val closeRepository: CloseRepositories
)