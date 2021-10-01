package com.mking11.androidDemo.common.entites.user.repositories

import androidx.lifecycle.LiveData
import com.mking11.androidDemo.common.entites.user.IUserDaoRepository
import com.mking11.androidDemo.common.entites.user.dao.UserDao
import com.mking11.androidDemo.common.entites.user.models.UserDataDbo
import com.mking11.androidDemo.common.entites.user.models.UserDataDto
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.utils.dao_utils.DaoRepo

class UserDaoRepository(dao: UserDao, private val firebaseCrash: FirebaseCrash) :
    DaoRepo<UserDataDbo, String, UserDao>(dao, "UserDaoRepository", firebaseCrash),
    IUserDaoRepository {

    override suspend fun insertOrUpdateUser(userDataDbo: UserDataDto) {
        super.insertOrUpdate(userDataDbo.toDbo())
    }

    override suspend fun deleteUser(userDataDbo: UserDataDto) {
        super.deleteItem(userDataDbo.toDbo())
    }

    override fun getUsers(): LiveData<List<UserDataDbo>>? = super.getItems()


}