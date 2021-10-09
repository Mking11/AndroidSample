package com.mking11.androidDemo.common.utils

import android.util.Patterns

fun isValidEmail(email: String): Boolean {
    return try {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } catch (e: Exception) {
        false
    }
}