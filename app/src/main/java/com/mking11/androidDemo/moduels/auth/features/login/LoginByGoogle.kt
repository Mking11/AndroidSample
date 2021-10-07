package com.mking11.androidDemo.moduels.auth.features.login


import android.app.Activity.RESULT_OK
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class LoginByGoogle(
    private val auth: FirebaseAuth
) {


    operator fun invoke(
        result: ActivityResult,
        errorMessage: String
    ): Flow<LoginState> = callbackFlow {

        trySend(
            LoginState(
                isOnProgress = true, isOnGoogleLogin = true
            )
        ).isSuccess


        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnSuccessListener { _ ->
                        // Sign in success, update UI with the signed-in user's information
//                        val user = auth.currentUser
                        this.trySend(
                            LoginState(
                                onSuccess = true,
                                isOnProgress = false,
                                isOnGoogleLogin = false
                            )
                        ).isSuccess

                    }.addOnFailureListener {
                        this.trySend(
                            LoginState(
                                onSuccess = false,
                                isOnProgress = false,
                                isOnGoogleLogin = false,
                                generalError = it.message ?: errorMessage
                            )
                        ).isSuccess

                    }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately

                println("error")
                trySend(
                    LoginState(
                        generalError = e.message ?: errorMessage,
                        isOnGoogleLogin = false,
                        isOnProgress = false
                    )
                ).isSuccess
            }

        } else {

            trySend(
                LoginState(
                    generalError = errorMessage,
                    isOnGoogleLogin = false,
                    isOnProgress = false
                )
            ).isSuccess
        }

        awaitClose { }
    }


}

