package com.mking11.androidDemo.common.utils.dao_utils

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

interface DaoCommon<Dto , PrimaryType> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: Dto): Long

    @Update
    fun update(item: Dto)

    @Delete
    fun deleteItem(item: Dto)

    @Transaction
    fun insertOrUpdate(item: Dto) {
        val result = insertItem(item)
        if (result == -1L) update(item)
    }


    fun clear()
    fun clearSelected(ids: List< @JvmSuppressWildcards PrimaryType>)
    fun getItems(): LiveData<List<@JvmSuppressWildcards Dto>>?
    fun getSelectedItems(ids: List< @JvmSuppressWildcards PrimaryType>): LiveData<List<@JvmSuppressWildcards Dto>>?
    fun getItem(id: PrimaryType): LiveData<Dto>?

}


