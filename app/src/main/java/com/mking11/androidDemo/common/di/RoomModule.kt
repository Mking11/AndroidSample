package com.mking11.androidDemo.common.di

import android.content.Context
import androidx.room.Room
import com.mking11.androidDemo.common.DemoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDemoDb(@ApplicationContext context: Context): DemoDatabase =
        Room.databaseBuilder(context, DemoDatabase::class.java, DemoDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

}