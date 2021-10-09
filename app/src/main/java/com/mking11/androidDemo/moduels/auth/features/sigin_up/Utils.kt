package com.mking11.androidDemo.moduels.auth.features.sigin_up

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mking11.androidDemo.common.utils.PhoneValidation
import com.mking11.androidDemo.moduels.auth.features.sigin_up.domain.model.CountryModel

fun getAsset(fileName: String, context: Context): String? {
    return try {
        context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    } catch (e: Exception) {
        null
    }
}

fun getCountryAsList(gson: Gson, context: Context, fileName: String): List<CountryModel>? {

    val jsonString: String? = getAsset(fileName, context = context)

    val type = object : TypeToken<List<CountryModel>>() {}.type


    return if (jsonString != null) {
        try {
            gson.fromJson<List<CountryModel>>(jsonString, type) ?: null
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}


fun isValidPhoneNumber(areaCode: String, phoneNumber: String): Boolean {
    return PhoneValidation().isPhoneNumberValid(phoneNumber, areaCode)

}