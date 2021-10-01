package com.mking11.androidDemo.common.utils.dao_utils

import androidx.lifecycle.LiveData
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.utils.repo_utils.DaoCommon
import com.mking11.androidDemo.common.utils.repo_utils.ScopeShared
import kotlinx.coroutines.launch

abstract class DaoRepo<OutPutType : Any, PrimaryKeyType, daoType : DaoCommon<OutPutType, PrimaryKeyType>>(
    private val dao: daoType,
    fileName: String,
    firebaseCrash: FirebaseCrash
) : ScopeShared(fileName, firebaseCrash) {

    fun insertOrUpdate(item: OutPutType?) = scope.launch(handler) {
        if (item != null) {
            dao.insertOrUpdate(item)
        }
    }

    fun deleteItem(item: OutPutType?) = scope.launch(handler) {
        if (item != null) {
            dao.deleteItem(item)
        }
    }

    fun clearTable() = scope.launch(handler) {
        dao.clear()
    }

    fun clearItems(ids: List<PrimaryKeyType>) = scope.launch(handler) {
        dao.clearSelected(ids)
    }

    fun getItems(): LiveData<List<OutPutType>>? = dao.getItems()


    fun getItems(items: List<PrimaryKeyType>): LiveData<List<OutPutType>>? =
        dao.getSelectedItems(items)


}