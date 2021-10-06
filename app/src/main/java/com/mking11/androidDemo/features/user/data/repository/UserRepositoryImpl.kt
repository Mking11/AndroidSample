package com.mking11.androidDemo.features.user.data.repository

import androidx.lifecycle.asFlow
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.utils.dao_utils.DaoRepo
import com.mking11.androidDemo.common.utils.repo_utils.FirebaseValueDataSource
import com.mking11.androidDemo.features.user.UserRepository
import com.mking11.androidDemo.features.user.data.data_source.UserDao
import com.mking11.androidDemo.features.user.domain.model.UserDataDbo
import com.mking11.androidDemo.features.user.domain.model.UserDataDto
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    dao: UserDao,
    firebaseCrash: FirebaseCrash,
    private val firebaseDataSource: FirebaseValueDataSource<UserDataDto>
) : DaoRepo<UserDataDbo, String, UserDao>(dao, "UserRepositoryImpl.kt", firebaseCrash),
    UserRepository {

    override suspend fun insertOrUpdateUserDb(userDataDbo: UserDataDto) {
        super.insertOrUpdate(userDataDbo.toDbo())
    }

    override suspend fun deleteUserDb(userDataDbo: UserDataDto) {
        super.deleteItem(userDataDbo.toDbo())
    }

    override fun getUsersDb(): Flow<List<@JvmSuppressWildcards UserDataDbo>>? = super.getItems()
    override fun fetchUserRemote(): Flow<HashMap<String, UserDataDto>> =
        firebaseDataSource.repoHashedLive.asFlow()

    override fun observeRemote() {
        firebaseDataSource.observeValue()
    }

    override fun closeRemote() {
        firebaseDataSource.closeRepository()
    }

    override fun closeRepository() {
        firebaseDataSource.closeRepository()
    }


}