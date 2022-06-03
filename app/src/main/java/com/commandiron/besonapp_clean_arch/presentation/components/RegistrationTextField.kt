package com.commandiron.besonapp_clean_arch.presentation.signup_steps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationTextField(
    size: DpSize = DpSize(240.dp, 56.dp),
    text: String,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onNext:() -> Unit = {},
    onChange:(String) -> Unit = {}
) {
    BasicTextField(
        modifier = Modifier
            .size(size)
            .background(
                Color.White,
                RoundedCornerShape(percent = 50)
            ),
        value = text,
        onValueChange = { onChange(it) },
        singleLine = true,
        maxLines = 1,
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction =  ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext()
            }
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(horizontal = size.height/3),
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
    )
}
