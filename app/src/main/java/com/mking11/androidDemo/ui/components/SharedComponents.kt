package com.mking11.androidDemo.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SmallErrorComponent(onError: Boolean, errorValue: String) {
    if (onError) {
        Text(
            errorValue,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Red
        )
    }
}
