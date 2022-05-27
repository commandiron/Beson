package com.commandiron.besonapp_clean_arch.presentation.signup_steps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        textStyle = MaterialTheme.typography.h5,
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
                        style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
                        )
                    )
                innerTextField()
            }
        }
    )
}
