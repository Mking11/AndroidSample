package com.mking11.androidDemo.common.utils


interface DboConvertor<T> {
    fun toDbo(key: String? = null): T? {
        return null
    }
}