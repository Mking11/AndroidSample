package com.mking11.androidDemo.common.firebaseutils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class FirebaseCrash @Inject constructor(private val firebaseAuthUtils: FirebaseAuthUtils) {
    var firebaseCrashlytics: FirebaseCrashlytics? = null

    fun getInstance(): FirebaseCrashlytics {
        return firebaseCrashlytics ?: FirebaseCrashlytics.getInstance()
    }


    fun setErrorToFireBase(
        e: Throwable? = null,
        source: String
    ) {
        val instance = getInstance()
        firebaseAuthUtils.getUserId()?.let { instance.setUserId(it) }
        if (e != null) {
            instance.setCustomKey("message", e.message.toString())
            instance.setCustomKey("stackTrace", e.stackTraceToString())
            instance.setCustomKey("source", source)
            instance.recordException(e)
        }
    }


    fun setErrorToFireBase(
        e: Exception? = null,
        source: String
    ) {
        val instance = getInstance()
        firebaseAuthUtils.getUserId()?.let { instance.setUserId(it) }

        if (e != null) {
            instance.setCustomKey("message", e.message.toString())
            instance.setCustomKey("stackTrace", e.stackTraceToString())
            instance.setCustomKey("cause", e.cause?.message.toString())
            instance.setCustomKey("source", source)
            instance.recordException(e)
        }
    }
}