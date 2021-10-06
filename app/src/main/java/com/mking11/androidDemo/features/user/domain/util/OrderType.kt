package com.mking11.androidDemo.features.user.domain.util

sealed class OrderType {
    object Ascending:OrderType()
    object Descending:OrderType()
}