package com.mking11.androidDemo.common.firebaseutils

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.internal.common.CommonUtils
import javax.inject.Inject

class FirebaseAuthRepo @Inject constructor(private val auth: FirebaseAuth) {

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    fun checkIfRotted(context: Context): Boolean {
        return CommonUtils.isRooted(context)
    }

    fun checkIfRunningOnEmulator(context: Context): Boolean {
        return CommonUtils.isEmulator(context)
    }

    fun checkIfTheAppIsDebugMode(context: Context): Boolean {
        return CommonUtils.isAppDebuggable(context) || CommonUtils.isDebuggerAttached()
    }

    fun isSingedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        return auth.signOut()
    }



}