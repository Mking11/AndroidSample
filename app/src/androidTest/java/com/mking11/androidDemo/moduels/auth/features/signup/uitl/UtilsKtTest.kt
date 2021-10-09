package com.mking11.androidDemo.moduels.auth.features.signup.uitl


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.mking11.androidDemo.moduels.auth.features.sigin_up.getAsset
import com.mking11.androidDemo.moduels.auth.features.sigin_up.getCountryAsList
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
    fun testGetFileAsStringAssertsNotNull() {

        val string = getAsset("CountryCodes.json", context)
        assertThat(string).isNotNull()
    }

    @Test
    fun testGetFileAsStringAssertsNull() {
        val string = getAsset("CountryCode.json", context)
        assertThat(string).isNull()
    }

    @Test
    fun testGetCountriesAsListListShouldntBeEmpty() {
        val gson = Gson()

        val items =
            getCountryAsList(gson, context, "CountryCodes.json" )

        println(items)
        Truth.assertThat(items).isNotNull()
    }


    @Test
    fun testGetCountriesAsListListShouldBeNull() {
        val gson = Gson()

        val items =
            getCountryAsList(gson, context, "CountryCdes.json" )

        println(items)
        Truth.assertThat(items).isNull()
    }



}