package com.mking11.androidDemo.moduels.auth.features.login.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.mking11.androidDemo.R
import com.mking11.androidDemo.moduels.auth.features.login.LoginState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UtilsKtTest {


    lateinit var context: Context


    @Before
    fun getContext() {
        context = ApplicationProvider.getApplicationContext()
    }


    @Test
    fun validEmailAssertsTrue() {
        val result: Boolean = isValidEmail("michaelboymek@gmail.com")
        Truth.assertThat(result).isTrue()
    }


    @Test
    fun emptyEmailAssertsFalse() {
        val result: Boolean = isValidEmail("")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun emailAssertsFalse() {
        val result = isValidEmail("e.com")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun email2AssertsTrue() {
        val result = isValidEmail("e@com.3t")
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun login_input_validation_email_error_required() {

        var passwordError = ""
        var emailError = ""
        var success: Boolean = loginInputValidation(
            "",
            "",
            onPassword =
            {
                passwordError = it
            },
            onEmailError =
            {
                emailError = it
            },

            )
        Truth.assertThat(passwordError).contains("Required")
        Truth.assertThat(emailError).contains("Required")
        Truth.assertThat(success).isFalse()
    }


    @Test
    fun login_input_validation_password_error_required() {
        var passwordError = ""
        var emailError = ""
        var success: Boolean = loginInputValidation(
            "michaelboymek@gmail.com",
            "",
            onPassword =
            {
                passwordError = it
            },
            onEmailError =
            {
                emailError = it
            },

            )
        Truth.assertThat(passwordError).contains("Required")
        Truth.assertThat(emailError).isEmpty()
        Truth.assertThat(success).isFalse()
    }

    @Test
    fun login_input_validation_password_valid() {
        var passwordError = ""
        var emailError = ""
        val success: Boolean = loginInputValidation(
            "michaelboymek@gmail.com",
            "123456789",
            onPassword =
            {
                passwordError = it
            }, onEmailError =
            {
                emailError = it
            }
        )
        Truth.assertThat(passwordError).isEmpty()
        Truth.assertThat(emailError).isEmpty()
        Truth.assertThat(success).isTrue()
    }


    @Test
    fun checkUserException() {
        val result = checkLoginException(
            FirebaseAuthInvalidUserException(
                "00", "Invalid user type"
            ), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.USER_CREDENTIAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.invalid_user))

    }

    @Test
    fun invalidUserTypeShouldReturnUserError() {

        val result = handleException(
            FirebaseAuthInvalidUserException(
                "00", "Invalid user type"
            ), context
        )

        Truth.assertThat(result.isOnProgress).isFalse()
        Truth.assertThat(result.userError).contains(context.getString(R.string.invalid_user))
    }

    @Test
    fun internalExceptionShouldThrowGeneral() {
        val result = checkLoginException(
            Exception("An internal error has occurred. [ 7: ]"), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.GENERAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.connection_issues))

    }

    @Test
    fun generalExceptionShouldThroughGeneralError() {
        val result = handleException(
            Exception("An internal error has occurred. [ 7: ]"), context
        )
        Truth.assertThat(result.generalError)
            .contains(context.resources.getString(R.string.connection_issues))
        Truth.assertThat(result.isOnProgress).isFalse()
    }


    @Test
    fun credentialExceptionTestShouldReturnErrorOnBothUserAndPassword() {
        val result = handleException(
            FirebaseAuthInvalidCredentialsException("00", "invalid user credentials"), context
        )
        Truth.assertThat(result.passwordError)
            .contains(context.getString(R.string.invalid_credentials))
        Truth.assertThat(result.userError).contains(context.getString(R.string.invalid_credentials))
        Truth.assertThat(result.isOnProgress).isFalse()
    }


    @Test
    fun credentialExceptionShouldThrowBoth() {
        val result = checkLoginException(
            FirebaseAuthInvalidCredentialsException("00", "invalid user credentials"), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.CREDENTIAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.invalid_credentials))
    }


    @Test
    fun tooManyResponseShouldShowGeneralError() {
        val result = handleException(
            FirebaseTooManyRequestsException("00"), context
        )

        Truth.assertThat(result.generalError)
            .contains(context.getString(R.string.too_many_requests))
        Truth.assertThat(result.isOnProgress).isFalse()
    }

    @Test
    fun tooManyAttemptsExceptionShouldThrowGeneral() {
        val result = checkLoginException(
            FirebaseTooManyRequestsException("00"), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.GENERAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.too_many_requests))

    }


    @Test
    fun emailErrorResponseShouldShowGeneralError() {
        val result = handleException(
            FirebaseAuthEmailException("00", "AuthError"), context
        )

        Truth.assertThat(result.userError)
            .contains(context.getString(R.string.invalid_email_error))
        Truth.assertThat(result.isOnProgress).isFalse()
    }

    @Test
    fun emailExceptionShouldThrowUserError() {
        val result = checkLoginException(
            FirebaseAuthEmailException("00", "AuthError"), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.USER_CREDENTIAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.invalid_email_error))

    }


    @Test
    fun updateResponseShouldShowGeneralError() {
        val result = handleException(
            FirebaseAuthActionCodeException("00", "Exception"), context
        )

        Truth.assertThat(result.generalError)
            .contains(context.getString(R.string.update_application))
        Truth.assertThat(result.isOnProgress).isFalse()
    }

    @Test
    fun updateResponseShouldThrowGeneralException() {
        val result = checkLoginException(
            FirebaseAuthActionCodeException("00", "Exception"), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.GENERAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.update_application))

    }

    @Test
    fun unknownErrorShouldShowGeneralError() {
        val result = handleException(
            Exception("00"), context
        )

        Truth.assertThat(result.generalError)
            .contains(context.getString(R.string.unknown_error))
        Truth.assertThat(result.isOnProgress).isFalse()
    }

    @Test
    fun unknownErrorShouldThrowGeneralException() {
        val result = checkLoginException(
            Exception("00"), context
        )

        Truth.assertThat(result.first)
            .isEquivalentAccordingToCompareTo(LoginErrorTypes.GENERAL)
        Truth.assertThat(result.second).contains(context.getString(R.string.unknown_error))

    }


}