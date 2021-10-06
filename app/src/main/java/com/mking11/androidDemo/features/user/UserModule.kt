package com.mking11.androidDemo.features.user

import com.mking11.androidDemo.common.DemoDatabase
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.firebaseutils.FirebaseRealDb
import com.mking11.androidDemo.features.user.data.data_source.UserDao
import com.mking11.androidDemo.features.user.data.data_source.UserFirebaseValueDataSource
import com.mking11.androidDemo.features.user.data.repository.UserRepositoryImpl
import com.mking11.androidDemo.features.user.domain.model.UserUseCases
import com.mking11.androidDemo.features.user.domain.use_cases.CloseRepositories
import com.mking11.androidDemo.features.user.domain.use_cases.FetchUsersRemote
import com.mking11.androidDemo.features.user.domain.use_cases.InsertUsersToDb
import com.mking11.androidDemo.features.user.domain.use_cases.ObserveRemote
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
    ): UserFirebaseValueDataSource =
        UserFirebaseValueDataSource(firebaseCrash, firebaseRealDb)


    @Provides
    @Singleton
    fun provideUserRepo(
        userDao: UserDao, firebaseCrash: FirebaseCrash,
        firebaseChildDataSource: UserFirebaseValueDataSource
    ): UserRepository = UserRepositoryImpl(userDao, firebaseCrash, firebaseChildDataSource)


    @Provides
    @Singleton
    fun providesUserUseCases(
        userRepository: UserRepository
    ): UserUseCases = UserUseCases(
        fetchRemote = FetchUsersRemote(userRepository),
        insertUsersToDb = InsertUsersToDb(userRepository),
        observeRemote = ObserveRemote(userRepository),
        closeRepository = CloseRepositories(userRepository)

    )
}