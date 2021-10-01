package com.mking11.androidDemo.common.entites.user.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mking11.androidDemo.common.entites.user.models.UserDataDbo
import com.mking11.androidDemo.common.entites.user.models.UserTableName
import com.mking11.androidDemo.common.utils.dao_utils.DaoCommon
import kotlinx.coroutines.flow.Flow


@Dao
abstract class UserDao : DaoCommon<UserDataDbo, String> {
    @Query("Select * From $UserTableName where id=:id")
    abstract override fun getItem(id: String): LiveData<UserDataDbo>

    @Query("Select * From $UserTableName")
    abstract override fun getItems(): LiveData<List<UserDataDbo>>

    @Query("Select * From $UserTableName where id in (:ids)")
    abstract override fun getSelectedItems(ids: List<String>): LiveData<List<UserDataDbo>>

    @Query("Delete  from $UserTableName where id not in (:ids)")
    abstract override fun clearSelected(ids: List<String>)

    @Query("Delete from $UserTableName")
    abstract override fun clear()

}