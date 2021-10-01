package com.mking11.androidDemo.common.utils.repo_utils


interface RepoCallback<T> {
    fun onAddedOrChanged(item:T)
    fun onDeleted(item: T)
}