package com.commandiron.besonapp_clean_arch.presentation.prices.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onChange:(String) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    BasicTextField(
        modifier = modifier
            .height(spacing.defaultSearchTextFieldHeight)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.background
            )
            .background(
                Color.White,
                RoundedCornerShape(percent = 50)
            ),
        value = text,
        onValueChange = { onChange(it) },
        singleLine = true,
        maxLines = 1,
        textStyle = MaterialTheme.typography.titleMedium,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier.padding(horizontal = spacing.defaultSearchTextFieldHeight/3),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty())
                        Text(
                            text = hint,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                            )
                        )
                    innerTextField()
                }
            }
        }
    )
}
