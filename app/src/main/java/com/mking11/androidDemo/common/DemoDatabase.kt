package com.mking11.androidDemo.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mking11.androidDemo.common.entites.user.dao.UserDao
import com.mking11.androidDemo.common.entites.user.models.UserDataDbo


@Database(entities = [UserDataDbo::class], version = 1, exportSchema = false)
abstract class DemoDatabase : RoomDatabase() {

    abstract val userDao:UserDao

    companion object {
        val DATABASE_NAME: String = "DemoDatabase"
    }
}