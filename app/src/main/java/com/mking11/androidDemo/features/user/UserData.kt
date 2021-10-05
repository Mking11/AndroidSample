package com.mking11.androidDemo.features.user

import com.mking11.androidDemo.common.models.CommonData

interface UserData : CommonData {
    val email:String
    val onLine:Boolean
    val userName: String
}