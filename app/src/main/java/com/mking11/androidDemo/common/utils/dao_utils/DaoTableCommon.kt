package com.mking11.androidDemo.common.utils.dao_utils

data class DaoTableCommon<PrimaryKeyType>(
    val tableName: String,
    val primaryKey: PrimaryKeyType,
)