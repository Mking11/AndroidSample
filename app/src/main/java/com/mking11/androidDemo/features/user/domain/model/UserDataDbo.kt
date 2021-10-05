package com.mking11.androidDemo.features.user.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mking11.androidDemo.features.user.UserData


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