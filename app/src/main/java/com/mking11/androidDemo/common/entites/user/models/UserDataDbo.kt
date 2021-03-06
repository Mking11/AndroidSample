package com.mking11.androidDemo.common.entites.user.models

import androidx.room.Entity
import androidx.room.PrimaryKey


const val UserTableName: String = "UserTable"


@Entity(tableName = UserTableName)
data class UserDataDbo(
    @PrimaryKey
    override val id: String = "",
    override val createdAt: String = "",
    override val updatedAt: String? = null,
    override val email: String = "",
    override val userName: String = "",
    override val onLine: Boolean = false
) : UserData