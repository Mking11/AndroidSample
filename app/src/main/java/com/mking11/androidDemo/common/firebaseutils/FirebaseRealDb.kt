package com.mking11.androidDemo.common.firebaseutils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject


class FirebaseRealDb @Inject constructor(
    private val realDb: FirebaseDatabase,
) {

    fun connectionState(): DatabaseReference {
        return realDb.getReference(".info/connected")
    }

    fun usersDocRef():DatabaseReference = realDb.getReference("users")


}