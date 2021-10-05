package com.mking11.androidDemo.common.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chip(
    category: String,
    isSelected: Boolean = false,
    isInverse: Boolean,
    modifier: Modifier = Modifier,
    onSelectedCategoryChanged: (Boolean) -> Unit,

    ) {

    val primary = if (isInverse) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
    val secondary = if (isInverse) MaterialTheme.colors.primary else MaterialTheme.colors.secondary

    val onPrimary =
        if (isInverse) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary
    val onSecondary =
        if (isInverse) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary

    val selected =
        if (isInverse) {
            !isSelected
        } else {
            isSelected
        }
    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                isSelected -> onPrimary
                else -> onSecondary
            }
        ), color = when {
            isSelected -> primary
            else -> secondary
        }
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(isSelected)
                }
            )) {

            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    start = 8.dp,
                    end = 8.dp
                ), fontSize = 14.sp
            )
        }
    }
}


@Composable
fun ChipComponent(
    category: String,
    isSelected: Boolean = false,
    isInverse: Boolean,
    modifier: Modifier = Modifier,
    onSelectedCategoryChanged: (Boolean) -> Unit,

    ) {

    val primary = if (isInverse) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
    val secondary = if (isInverse) MaterialTheme.colors.primary else MaterialTheme.colors.secondary

    val onPrimary =
        if (isInverse) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary
    val onSecondary =
        if (isInverse) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary

    val selected =
        if (isInverse) {
            !isSelected
        } else {
            isSelected
        }
    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                isSelected -> onPrimary
                else -> onSecondary
            }
        ), color = when {
            isSelected -> primary
            else -> secondary
        }
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(isSelected)
                }
            )) {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(start = 5.dp),
                    tint = MaterialTheme.colors.surface
                )
            }

            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    start = if (selected) 2.dp else 8.dp,
                    end = 8.dp
                ), fontSize = 14.sp
            )
        }
    }
}


@Composable
fun LabeledOptionChip(
    label: String,
    errorValue: String,
    modifier: Modifier,
    option1: String,
    option2: String,
    option1Selected: Boolean?,
    onError: Boolean,
    onOptionSelected: (Boolean) -> Unit,

    ) {
    Column(modifier) {
        Text(label, modifier = Modifier.padding(top = 10.dp))
        SmallErrorComponent(onError, errorValue)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        ) {
            Chip(
                option1,
                if (option1Selected == null) false else option1Selected == true,
                false,
                modifier = Modifier.padding(5.dp)
            ) {
                onOptionSelected(false)
            }
            Spacer(modifier = Modifier.padding(start = 5.dp, end = 5.dp))

            Chip(
                option2,
                if (option1Selected == null) true else option1Selected == true,
                true,
                modifier = Modifier.padding(5.dp)
            ) {
                onOptionSelected(true)
            }
        }
    }

}


private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}

@Composable
fun PodcastOptionTabs(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (Int) -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val selectedIndex = options.indexOfFirst { it == selectedOption }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex, divider = {},
        /* Disable the built-in divider */
        edgePadding = 24.dp,
        indicator = emptyTabIndicator,
        modifier = modifier, backgroundColor = backgroundColor
    ) {
        options.forEachIndexed { index, Option ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onOptionSelected(index) }
            ) {
                ChoiceChipContent(
                    text = Option,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.secondaryVariant.copy(alpha = 0.2f)
            else -> MaterialTheme.colors.secondary.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> MaterialTheme.colors.onSecondary
            else -> MaterialTheme.colors.secondary
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun CheckboxWithText(text: String, selected: Boolean, onSelection: (Boolean) -> Unit) {
    Row(modifier = Modifier.width(150.dp).padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        Text(text,
            textAlign = TextAlign.Start,
            modifier = Modifier.offset(x = 5.dp).padding(end = 8.dp))
        Checkbox(selected, {
            onSelection(it)
        })
    }
}


