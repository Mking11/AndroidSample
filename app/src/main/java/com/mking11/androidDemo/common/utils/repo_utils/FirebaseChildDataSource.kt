package com.mking11.androidDemo.common.utils.repo_utils

import com.google.firebase.database.*
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash


abstract class FirebaseChildDataSource<T>(private val firebaseCrash: FirebaseCrash) {
    abstract val docRef: DatabaseReference
    lateinit var repoCallback: RepoCallback<T>

    open val childListener: ChildEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            this@FirebaseChildDataSource.onChildAdded(snapshot, previousChildName)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            this@FirebaseChildDataSource.onChildChanged(snapshot, previousChildName)
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            this@FirebaseChildDataSource.onChildDeleted(snapshot)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            this@FirebaseChildDataSource.onChildMoved(snapshot, previousChildName)
        }

        override fun onCancelled(error: DatabaseError) {
            this@FirebaseChildDataSource.onCancelled(error)
        }

    }

    abstract fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?)

    open fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

    abstract fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?)

    open fun onChildDeleted(snapshot: DataSnapshot) {}

    fun onCancelled(exception: DatabaseError) {
        firebaseCrash.setErrorToFireBase(exception.toException(), "onCancelled RepoOpen.kt  60: ")
    }


    open fun observeValue() {
        docRef.addChildEventListener(childListener)
    }

    open fun closeRepository() {
        docRef.removeEventListener(childListener)
    }


    open fun observeValue(_callback: RepoCallback<T>) {
        repoCallback = _callback
        observeValue()
    }


}