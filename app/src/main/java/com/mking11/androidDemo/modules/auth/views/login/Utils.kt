package com.mking11.androidDemo.modules.auth.views.login

import android.content.Context
import android.util.Patterns

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
        onEmailError(if (context != null) "" else "Required")
        response = false
    } else if (!isValidEmail(userCredential)) {
        onEmailError(if (context != null) "" else "Invalid Email address ")
        response = false
    }


    if (userPassword.isEmpty()) {
        onPassword(if (context != null) "" else "Required")
        response = false
    }
    return response
}



