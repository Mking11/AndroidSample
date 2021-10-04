package com.mking11.androidDemo.modules.auth.views.login


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsKtTest {

    @Test
    fun `empty email returns false`() {
        val result: Boolean = isValidEmail("michaelboymek@gmail.com")
        assertThat(result).isFalse()
    }
}