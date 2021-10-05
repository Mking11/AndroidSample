package com.mking11.androidDemo.features.user.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.mking11.androidDemo.common.utils.repo_utils.DaoCommon
import com.mking11.androidDemo.features.user.domain.model.UserDataDbo
import com.mking11.androidDemo.features.user.domain.model.UserTableName
import kotlinx.coroutines.flow.Flow


@Dao
abstract class UserDao : DaoCommon<UserDataDbo, String> {
    @Query("Select * From $UserTableName where id=:id")
    abstract override suspend fun getItem(id: String): UserDataDbo?


    @Query("Select * From $UserTableName")
    abstract override fun getItems(): Flow<List<@JvmSuppressWildcards UserDataDbo>>?

    @Query("Select * From $UserTableName where id in (:ids)")
    abstract override fun getSelectedItems(ids: List<String>): Flow<List<UserDataDbo>>

    @Query("Delete  from $UserTableName where id not in (:ids)")
    abstract override suspend fun clearSelected(ids: List<String>)

    @Query("Delete from $UserTableName")
    abstract override suspend fun clear()

}