package com.mking11.androidDemo.moduels.auth.features.sigin_up.use_cases

import android.content.Context
import com.google.gson.Gson
import com.mking11.androidDemo.moduels.auth.features.sigin_up.domain.model.CountryModel
import com.mking11.androidDemo.moduels.auth.features.sigin_up.getCountryAsList

class GetCountries(private val gson: Gson, private val context: Context) {
    operator fun invoke(
        countryFileName: String = "CountryCodes.json"
    ): List<CountryModel>? {
        return getCountryAsList(gson, context, countryFileName)
    }


}