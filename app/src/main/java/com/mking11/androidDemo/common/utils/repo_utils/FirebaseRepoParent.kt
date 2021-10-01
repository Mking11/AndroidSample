package com.mking11.androidDemo.common.utils.repo_utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash


abstract class FirebaseRepoParent<T>(private val firebaseCrash: FirebaseCrash) {
    abstract val docRef: DatabaseReference
    private val _repoHashedLive = MutableLiveData<HashMap<String, T>>()
    private val _repoLive = MutableLiveData<T>()
    val repoLive: LiveData<T> = _repoLive


    lateinit var repoCallback: RepoCallback<T>

    val valueListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            this@FirebaseRepoParent.onDataChanged(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            this@FirebaseRepoParent.onCancelled(error)
        }
    }

    open fun onDataChanged(snapshot: DataSnapshot) {}

    val childListener: ChildEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            this@FirebaseRepoParent.onChildAdded(snapshot, previousChildName)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            this@FirebaseRepoParent.onChildChanged(snapshot, previousChildName)
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            this@FirebaseRepoParent.onChildDeleted(snapshot)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            this@FirebaseRepoParent.onChildMoved(snapshot, previousChildName)
        }

        override fun onCancelled(error: DatabaseError) {
            this@FirebaseRepoParent.onCancelled(error)
        }

    }

    open fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {}

    open fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

    open fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

    open fun onChildDeleted(snapshot: DataSnapshot) {}

    open fun onCancelled(exception: DatabaseError) {
        firebaseCrash.setErrorToFireBase(exception.toException(), "onCancelled RepoOpen.kt  60: ")
    }

    fun setDataToLiveData(data: T?) {
        if (data != null) {
            _repoLive.value = data!!
        }
    }


    abstract fun observeValue()
    abstract fun closeRepository()


    open fun observeValue(_callback: RepoCallback<T>) {
        repoCallback = _callback
        observeValue()
    }


    fun getHashValues(): MutableLiveData<HashMap<String, T>> {
        return _repoHashedLive
    }


    fun getValue(): MutableLiveData<T> {
        return _repoLive
    }


}