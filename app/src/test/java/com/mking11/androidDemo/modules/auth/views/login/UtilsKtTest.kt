package com.mking11.androidDemo.modules.auth.views.login


import com.google.common.truth.Truth.assertThat
import com.mking11.androidDemo.moduels.auth.features.login.util.isValidEmail
import org.junit.Test

class UtilsKtTest {

    @Test
    fun `empty email returns false`() {
        val result: Boolean = isValidEmail("michaelboymek@gmail.com")
        assertThat(result).isFalse()
    }
}