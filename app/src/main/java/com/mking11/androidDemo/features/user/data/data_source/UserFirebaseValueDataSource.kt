package com.mking11.androidDemo.features.user.data.data_source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.firebaseutils.FirebaseRealDb
import com.mking11.androidDemo.common.utils.repo_utils.FirebaseValueDataSource
import com.mking11.androidDemo.features.user.domain.model.UserDataDto

class UserFirebaseValueDataSource(
    private val firebaseCrash: FirebaseCrash,
    private val realDb: FirebaseRealDb
) : FirebaseValueDataSource<UserDataDto>() {

    override val docRef: DatabaseReference
        get() = realDb.usersDocRef()

    override fun onDataChanged(snapshot: DataSnapshot) {
        try {
            val data = snapshot.getValue<HashMap<String, UserDataDto>>()
            repoHashedLive.postValue(data ?: hashMapOf())
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(
                e,
                "onDataChanged UserFirebaseValueDataSource.kt  28: "
            )
        }
    }

    override fun onCancelled(error: DatabaseError) {
        repoHashedLive.postValue(null)
        firebaseCrash.setErrorToFireBase(
            error.toException(),
            "onCancelled UserFirebaseValueDataSource.kt  36: "
        )
    }


}