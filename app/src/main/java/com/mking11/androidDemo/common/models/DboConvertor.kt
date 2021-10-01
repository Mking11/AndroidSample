package com.mking11.androidDemo.common.models


interface DboConvertor<T> {
    fun toDbo(key: String? = null): T? {
        return null
    }
}