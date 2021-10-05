package com.mking11.androidDemo.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mking11.androidDemo.R


@Composable
fun ConnectionProblem(retry: () -> Unit) {
    Dialog(
        onDismissRequest = {

        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card {
            Column(
                Modifier.width(300.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.no_connection_dark),
                    "Connection issues",
                    modifier = Modifier.height(100.dp).width(100.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 20.dp, bottom = 10.dp),
                )
                Text(
                    "Connection Problems",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface
                )

                Button(
                    { retry() },
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        "Retry",
                        color = MaterialTheme.colors.onSurface
                    )
                }


            }
        }

    }
}
