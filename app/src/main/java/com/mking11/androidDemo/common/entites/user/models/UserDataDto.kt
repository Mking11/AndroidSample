package com.mking11.androidDemo.common.entites.user.models

import com.mking11.androidDemo.common.utils.DboConvertor


data class UserDataDto(
    override val id: String = "",
    override val createdAt: String = "",
    override val updatedAt: String? = null,
    override val email: String = "",
    override val userName: String = "",
    override val onLine: Boolean = false
) : UserData, DboConvertor<UserDataDbo> {
    override fun toDbo(key: String?): UserDataDbo {
        return UserDataDbo(id, createdAt, updatedAt, email, userName, onLine)
    }
}