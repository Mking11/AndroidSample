package com.mking11.androidDemo.moduels.auth.features.sigin_up.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mking11.androidDemo.R
import com.mking11.androidDemo.common.presentation.components.DropDownList
import com.mking11.androidDemo.common.presentation.components.OutlinedTextFieldWithError

@Composable
fun SignUpComponents() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextFieldWithError(
            "",
            {},
            false,
            "",
            stringResource(R.string.email),
            type = KeyboardType.Email,
            modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
        )

        DropDownList(
            list = listOf(),
            label = stringResource(R.string.country),
            onError = false,
            errorValue = "",
            selectedOption = "",
            modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            paddingValues = PaddingValues(top = 2.dp,start = 30.dp,end = 30.dp)
        ) {

        }

        OutlinedTextFieldWithError(
            "",
            {},
            false,
            "",
            stringResource(R.string.phone_number),
            type = KeyboardType.Email,
            modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
        )

        Button(
            {}, modifier = Modifier
                .padding(top = 40.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.sign_up))
        }

    }
}