package com.mking11.androidDemo.common.entites.user

import com.mking11.androidDemo.common.DemoDatabase
import com.mking11.androidDemo.common.entites.user.dao.UserDao
import com.mking11.androidDemo.common.entites.user.models.UserDataDto
import com.mking11.androidDemo.common.entites.user.repositories.UserDaoRepository
import com.mking11.androidDemo.common.entites.user.repositories.UserFirebaseRepository
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.firebaseutils.FirebaseRealDb
import com.mking11.androidDemo.common.utils.repo_utils.FirebaseRepoParent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object UserModule {

    @Provides
    @Singleton
    fun provideUserDao(demoDatabase: DemoDatabase) = demoDatabase.userDao

    @Provides
    @Singleton
    fun provideUserFirebaseRepo(
        firebaseCrash: FirebaseCrash,
        firebaseRealDb: FirebaseRealDb
    ): FirebaseRepoParent<UserDataDto> = UserFirebaseRepository(firebaseCrash, firebaseRealDb)

    @Provides
    @Singleton
    fun provideUserDaoRepo(
        userDao: UserDao,
        firebaseCrash: FirebaseCrash
    ): IUserDaoRepository = UserDaoRepository(userDao, firebaseCrash)
}