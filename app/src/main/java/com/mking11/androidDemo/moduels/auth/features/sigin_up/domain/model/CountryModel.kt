package com.mking11.androidDemo.moduels.auth.features.sigin_up.domain.model

import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("name") val name: String,
    @SerializedName("flag") val flag: String,
    @SerializedName("dial_code") val dialCode: String,
    @SerializedName("code") val code: String
)

data class CountryList(
    val countries: List<CountryModel> = listOf()
)