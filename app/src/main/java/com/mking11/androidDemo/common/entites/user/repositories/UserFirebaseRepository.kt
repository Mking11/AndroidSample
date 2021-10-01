package com.mking11.androidDemo.common.entites.user.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.mking11.androidDemo.common.entites.user.models.UserDataDto
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash
import com.mking11.androidDemo.common.firebaseutils.FirebaseRealDb
import com.mking11.androidDemo.common.utils.repo_utils.FirebaseRepoParent

class UserFirebaseRepository(
    private val firebaseCrash: FirebaseCrash,
    private val realDb: FirebaseRealDb
) :
    FirebaseRepoParent<UserDataDto>(firebaseCrash) {

    override val docRef: DatabaseReference
        get() = realDb.usersDocRef()


    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        onDataChangedOrAdded(snapshot)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        onDataChangedOrAdded(snapshot)
    }

    private fun onDataChangedOrAdded(snapshot: DataSnapshot) {
        try {
            val data = snapshot.getValue<UserDataDto>()
            if (data != null) {
                repoCallback.onAddedOrChanged(data)
            }
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(
                e,
                "onDataChangedOrAdded UserFirebaseRepository.kt  38: "
            )
        }
    }


    override fun onChildDeleted(snapshot: DataSnapshot) {
        try {
            val data = snapshot.getValue<UserDataDto>()
            if (data != null) {
                repoCallback.onDeleted(data)
            }
        } catch (e: Exception) {
            firebaseCrash.setErrorToFireBase(e, "onChildDeleted UserFirebaseRepository.kt  49: ")
        }
    }

    override fun observeValue() {
        docRef.addChildEventListener(childListener)
    }

    override fun closeRepository() {
        docRef.removeEventListener(childListener)
    }
}