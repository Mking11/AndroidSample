package com.mking11.androidDemo.common.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun DropDownList(
    modifier: Modifier = Modifier,
    label: String,
    onError: Boolean,
    errorValue: String,
    selectedOption: String,
    paddingValues: PaddingValues,
    list: List<String>,
    fontSize: Int = 16,
    onItemSelected: (String) -> Unit,
) {


    val expanded = remember { mutableStateOf(false) }


    val color = when {
        onError -> {
            Color.Red
        }

        expanded.value -> {
            Color.Blue
        }

        else -> {
            Color.LightGray
        }
    }


    val icons = if (expanded.value)
        Icons.Default.ArrowDropUp
    else
        Icons.Default.ArrowDropDown

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            label,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp, start = 5.dp, bottom = 4.dp),
        )
        Box(
            modifier = Modifier
                .height(50.dp)
                .border(
                    border = BorderStroke(1.5.dp, color),
                    shape = RoundedCornerShape(6.dp)
                )
                .fillMaxWidth()
        ) {


            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        expanded.value = !expanded.value
                    }
                    .padding(start = 20.dp, end = 20.dp)
            ) {


                val (text, dropdown, icon) = createRefs()
                Text(
                    text = if (selectedOption == "") "Please select one" else selectedOption,
                    fontSize = fontSize.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(text) {
                            start.linkTo(parent.start, 5.dp)
                            end.linkTo(icon.start)
                            linkTo(parent.top, parent.bottom)
                        }, textAlign = TextAlign.Center

                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.fillMaxWidth()
                        .padding(paddingValues)
                        .constrainAs(
                            dropdown
                        ) {
                            start.linkTo(parent.start, 20.dp)
                            end.linkTo(parent.end)
                            top.linkTo(parent.bottom, 20.dp)
                        }
                ) {
                    list.forEach { label ->
                        DropdownMenuItem(onClick = {
                            onItemSelected(label)
                            expanded.value = false
                        }, modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
                            Text(text = label, maxLines = 1)
                        }
                    }
                }
                IconButton(
                    onClick = { expanded.value = !expanded.value },
                    modifier = Modifier.constrainAs(icon) {
                        start.linkTo(text.end)
                        end.linkTo(parent.end, 3.dp)
                    }) {
                    Icon(imageVector = icons, "drop down")
                }
            }

        }
        SmallErrorComponent(onError, errorValue)
    }


}


@Composable
fun DropDownListWithText(
    label: String,
    onError: Boolean,
    errorValue: String,
    selected: String,
    list: List<String>,
    onItemSelected: (String) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }

    val icon = if (expanded.value)
        Icons.Default.ArrowDropUp
    else
        Icons.Default.ArrowDropDown

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(value = selected,
            onValueChange = { result ->
                list.filter {
                    it.contains(result, true)
                }
            },
            label = { Text(label) },
            isError = onError,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    icon,
                    "contentDescription",
                    Modifier.clickable { expanded.value = !expanded.value })
            })

        SmallErrorComponent(onError, errorValue)

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    onItemSelected(label)
                    expanded.value = false
                }) {
                    Text(text = label)
                }
            }
        }
    }


}

