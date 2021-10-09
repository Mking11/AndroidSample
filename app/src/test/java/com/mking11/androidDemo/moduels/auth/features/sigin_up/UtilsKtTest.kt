package com.mking11.androidDemo.moduels.auth.features.sigin_up

import com.google.common.truth.Truth
import org.junit.Test


class UtilTests {

    @Test

    fun `invalid phone number should assert false`() {
        val result = isValidPhoneNumber("+251", "random")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid phone number should assert true`() {
        val result = isValidPhoneNumber("+251", "+251911112565")
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `valid phone but invalid format should assert false`(){
        val result = isValidPhoneNumber("+251","9489856")
        Truth.assertThat(result).isFalse()
    }

}
