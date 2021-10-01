package com.mad.jemla.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun SuccessContent(onDismiss: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            "Success",
            tint = Color.Green,
            modifier = Modifier.height(100.dp).width(100.dp).padding(top = 20.dp)
        )
        Text(
            "Success", textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            color = MaterialTheme.colors.onSurface
        )

        Button(
            onClick = {
                onDismiss()
            },
            modifier = Modifier.padding(
                top = 10.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 10.dp
            ).fillMaxWidth()
        ) {
            Text(
                "Close",
                color = MaterialTheme.colors.background,
                style = MaterialTheme.typography.h6
            )
        }
    }

}


@Composable
fun SuccessDailog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            SuccessContent {
                onDismiss()
            }
        }
    }
}
