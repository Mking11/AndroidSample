package com.mking11.androidDemo.moduels.auth.features.login.util

import android.content.Context
import android.util.Patterns
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.mking11.androidDemo.R
import com.mking11.androidDemo.moduels.auth.features.login.domain.models.LoginState
import com.mking11.androidDemo.moduels.auth.features.login.domain.utils.LoginErrorTypes

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


fun checkLoginException(e: Exception, context: Context): Pair<LoginErrorTypes, String> {
    return when {
        e.message.toString() == "An internal error has occurred. [ 7: ]" -> {
            Pair(LoginErrorTypes.GENERAL, context.getString(R.string.connection_issues))
        }
        e is FirebaseAuthInvalidCredentialsException -> {
            Pair(LoginErrorTypes.CREDENTIAL, context.getString(R.string.invalid_credentials))
        }
        e is FirebaseAuthInvalidUserException -> {
            Pair(LoginErrorTypes.USER_CREDENTIAL, context.getString(R.string.invalid_user))
        }
        e is FirebaseTooManyRequestsException -> {
            Pair(LoginErrorTypes.GENERAL, context.getString(R.string.too_many_requests))
        }
        e is FirebaseAuthEmailException -> {
            Pair(LoginErrorTypes.USER_CREDENTIAL, context.getString(R.string.invalid_email_error))
        }
        e is FirebaseAuthActionCodeException -> {
            Pair(LoginErrorTypes.GENERAL, context.getString(R.string.update_application))
        }
        else -> {
            Pair(LoginErrorTypes.GENERAL, context.getString(R.string.unknown_error))
        }
    }
}

fun LoginState.handleException(e: Exception, context: Context): LoginState {
    val exception = checkLoginException(e, context)
    return when (exception.first) {
        LoginErrorTypes.GENERAL -> copy(isOnProgress = false, generalError = exception.second)
        LoginErrorTypes.CREDENTIAL -> copy(
            isOnProgress = false,
            userError = exception.second,
            passwordError = exception.second
        )
        LoginErrorTypes.USER_CREDENTIAL -> copy(
            isOnProgress = false,
            userError = exception.second
        )
        LoginErrorTypes.PASSWORD -> copy()
    }
}
