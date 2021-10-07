package com.mking11.androidDemo.common.firebaseutils

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.internal.common.CommonUtils
import com.mking11.androidDemo.R
import com.mking11.androidDemo.common.models.AppResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FirebaseAuthUtils @Inject constructor(
    private val auth: FirebaseAuth
) {

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


    fun userLogin(user: String, password: String): Flow<AppResult<String>> = callbackFlow {
        this.trySend(AppResult.InProgress).isSuccess
        auth.signInWithEmailAndPassword(user, password)
            .addOnSuccessListener { authResponse ->
                if (authResponse.user?.uid != null) {
                    this.trySend(AppResult.Success(authResponse.user?.uid!!))
                } else {
                    this.trySend(
                        AppResult.Error.RecoverableError(
                            exception = FirebaseAuthInvalidUserException(
                                "00",
                                "Unknown User"
                            )
                        )
                    )
                }
            }.addOnFailureListener { e ->
                this.trySend(
                    AppResult.Error.RecoverableError(
                        Exception(
                            e
                        )
                    )
                ).isFailure
            }


        awaitClose { }
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        context: Context
    ): Flow<AppResult<String>> =
        callbackFlow {
            this.trySend(AppResult.InProgress).isSuccess
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        if (!user?.uid.isNullOrEmpty()) {
                            this.trySend(AppResult.Success(user?.uid!!)).isSuccess
                        } else {
                            this.trySend(
                                AppResult.Failure(
                                    null,
                                    context.getString(R.string.unknown_error)
                                )
                            )
                        }
                    } else {
                        this.trySend(
                            AppResult.Failure(
                                null,
                                context.getString(R.string.unknown_error)
                            )
                        )
                    }
                }

            awaitClose { }
        }


}