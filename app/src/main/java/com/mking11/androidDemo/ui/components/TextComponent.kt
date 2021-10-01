package com.mking11.androidDemo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedParagraphTextFieldWithError(
    fieldValue: String,
    onValueChanged: (String) -> Unit,
    onError: Boolean,
    errorValue: String,
    fieldLabel: String,
    type: KeyboardType,
    maxLines: Int,
    height: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = fieldValue,
            onValueChange = { result ->
                onValueChanged(result)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp),
            label = {
                Text(fieldLabel)
            },
            isError = onError, maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = type)

        )
        SmallErrorComponent(onError, errorValue)
    }

}


@Composable
fun OutlinedTextFieldWithError(
    fieldValue: String,
    onValueChanged: (String) -> Unit,
    onError: Boolean,
    errorValue: String,
    fieldLabel: String,
    type: KeyboardType,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = fieldValue,
            onValueChange = { result ->
                onValueChanged(result)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(fieldLabel)
            },
            isError = onError,
            keyboardOptions = KeyboardOptions(keyboardType = type),

            )
        SmallErrorComponent(onError, errorValue)
    }

}

@Composable
fun OutlinedTextFieldWithErrorCentered(
    modifier: Modifier = Modifier,
    fieldValue: String,
    onValueChanged: (String) -> Unit,
    onError: Boolean,
    errorValue: String,
    fieldLabel: String,
    type: KeyboardType,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,

) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = fieldValue,
            onValueChange = { result ->
                onValueChanged(result)
            },
            modifier = modifier.fillMaxWidth(),
            label = {
                Text(fieldLabel)
            },
            isError = onError,
            keyboardOptions = KeyboardOptions(keyboardType = type),
            maxLines = maxLines,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)

        )
        SmallErrorComponent(onError, errorValue)
    }

}


@Composable
fun PasswordOutlinedTextFieldWithError(
    fieldValue: String,
    onValueChanged: (String) -> Unit,
    onError: Boolean,
    errorValue: String,
    fieldLabel: String,
    modifier: Modifier = Modifier,
) {
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {

        OutlinedTextField(
            value = fieldValue,
            onValueChange = { result ->
                onValueChanged(result)
            },
            label = { Text(fieldLabel) },
            isError = onError,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {

                val image =
                    if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }
                ) {
                    Icon(imageVector = image, contentDescription = "change visiblity")

                }
            }
        )
        SmallErrorComponent(onError, errorValue)
    }


}
