package com.mking11.androidDemo.common.utils.dao_utils

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.utils.repo_utils.ScopeShared
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class DaoRepo<OutPutType : Any, PrimaryKeyType, daoType : DaoCommon<OutPutType, PrimaryKeyType>>(
    firebaseCrash: FirebaseCrash,
    fileName: String,
    private val dao: daoType
) : ScopeShared(fileName, "IDaoRepo", firebaseCrash) {


    fun insertOrUpdate(item: OutPutType?) = scope.launch {
        if (item != null) {
            dao.insertOrUpdate(item)
        }
    }

    fun deleteItem(item: OutPutType?) = scope.launch {
        if (item != null) {
            dao.deleteItem(item)
        }
    }

    fun clearItems(ids: List<PrimaryKeyType>) = scope.launch {
        dao.clearSelected(ids)
    }

    fun getItems(): LiveData<List<OutPutType>>? = dao.getItems()


    fun getItems(items: List<PrimaryKeyType>): LiveData<List<OutPutType>>? =
        dao.getSelectedItems(items)


}