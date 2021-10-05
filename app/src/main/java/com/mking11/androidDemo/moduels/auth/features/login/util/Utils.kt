package com.mking11.androidDemo.moduels.auth.features.login.util

import android.content.Context
import android.util.Patterns
import com.mking11.androidDemo.R

fun isValidEmail(email: String): Boolean {
    return try {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } catch (e: Exception) {
        false
    }
}


fun loginInputValidation(
    userCredential: String,
    userPassword: String,
    context: Context? = null,
    onPassword: (String) -> Unit,
    onEmailError: (String) -> Unit,
): Boolean {
    var response = true

    if (userCredential.isEmpty()) {
        onEmailError(context?.getString(R.string.required) ?: "Required")
        response = false
    } else if (!isValidEmail(userCredential)) {
        onEmailError(context?.getString(R.string.invalid_email) ?: "Invalid Email address")
        response = false
    }

    if (userPassword.isEmpty()) {
        onPassword(context?.getString(R.string.required) ?: "Required")
        response = false
    }
    return response
}



