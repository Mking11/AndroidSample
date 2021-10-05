package com.mking11.androidDemo.common.utils.repo_utils

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

abstract class FirebaseValueDataSource<T> {
    abstract val docRef: DatabaseReference
    open val repoHashedLive = MutableLiveData<HashMap<String, T>>()
    open val repoLive = MutableLiveData<T>()


    open val valueListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            this@FirebaseValueDataSource.onDataChanged(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            this@FirebaseValueDataSource.onCancelled(error)
        }
    }

    abstract fun onDataChanged(snapshot: DataSnapshot)
    abstract fun onCancelled(error: DatabaseError)


    open fun observeValue() {
        docRef.addValueEventListener(valueListener)
    }

    open fun closeRepository() {
        docRef.removeEventListener(valueListener)
    }


}