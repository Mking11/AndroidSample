package com.mking11.androidDemo.common.utils.repo_utils

import androidx.lifecycle.LiveData
import androidx.room.*

interface DaoCommon<Dto, PrimaryType> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: Dto): Long

    @Update
    fun update(item: Dto)

    @Delete
    suspend fun deleteItem(item: Dto)

    @Transaction
    fun insertOrUpdate(item: Dto) {
        val result = insertItem(item)
        if (result == -1L) update(item)
    }


    suspend fun clear()
    suspend fun clearSelected(ids: List<@JvmSuppressWildcards PrimaryType>)
    fun getItems(): LiveData<List<@JvmSuppressWildcards Dto>>?
    fun getSelectedItems(ids: List<@JvmSuppressWildcards PrimaryType>): LiveData<List<@JvmSuppressWildcards Dto>>?
    fun getItem(id: PrimaryType): LiveData<Dto>?

}


