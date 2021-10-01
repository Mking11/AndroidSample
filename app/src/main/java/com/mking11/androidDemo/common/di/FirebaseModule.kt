package com.mking11.androidDemo.common.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mking11.androidDemo.BuildConfig
import com.mking11.androidDemo.BuildConfig.BUILD_TYPE
import com.mking11.androidDemo.common.firebaseutils.FirebaseAuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {


    @Singleton
    @Provides
    fun provideRealDb(): FirebaseDatabase {
        var realDb: FirebaseDatabase? = null
        if (realDb == null) {
            when (BuildConfig.BUILD_TYPE) {
                "debug" -> {
                    realDb =
                        FirebaseDatabase.getInstance("https://androiddemo-debb9-default-rtdb.europe-west1.firebasedatabase.app")
                    realDb.setPersistenceEnabled(true)
//                    realDb?.useEmulator(BuildConfig.IP_ADDRESS, 9000)
                }

                else -> {
                    realDb =
                        FirebaseDatabase.getInstance("https://androiddemo-debb9-default-rtdb.europe-west1.firebasedatabase.app")
                    realDb.setPersistenceEnabled(true)

                }

            }
        }
        return realDb
    }

    @Singleton
    @Provides
    fun providesAuth(): FirebaseAuth {
        return when (BUILD_TYPE) {
            "debug" -> {
                FirebaseAuth.getInstance()
                // mAuth.useEmulator(ip, 9099)
            }

            else -> {
                FirebaseAuth.getInstance()
            }

        }
    }

    @Singleton
    @Provides
    fun providesAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthRepo(auth: FirebaseAuth): FirebaseAuthRepo {
        return FirebaseAuthRepo(auth)
    }

    @UserId
    @Singleton
    @Provides
    fun providesUserId(auth: FirebaseAuth): String? {
        return if (auth.currentUser != null) {
            auth.currentUser!!.uid
        } else {
            null
        }
    }

}