package com.mking11.androidDemo.common.repositories

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.mking11.androidDemo.common.firebaseutils.FirebaseCrash


const val SHARED_USER_ID = "USER_ID"
const val DEFAULT = "DEFAULT"

class PreferenceRepository(
    private val sharedPreferences: SharedPreferences,
    private val context: Context,
    private val firebaseCrash: FirebaseCrash,
    private val auth: FirebaseAuth,
) {


    var userId: String? = DEFAULT
        get() = getUserIds()
        set(value) {
            sharedPreferences.edit()
                .putString(SHARED_USER_ID, value)
                .apply()

            field = value
        }


    private fun getUserIds(): String? {
        val user = sharedPreferences.getString(SHARED_USER_ID, DEFAULT)
        return auth.currentUser?.uid ?: user
    }


}