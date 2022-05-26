package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
fun FormTextField(
    size: DpSize = DpSize(240.dp, 42.dp),
    text: String,
    hint: String,
    textFieldErrorMessage: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChange:(String) -> Unit = {}
) {
    val localFocusManager = LocalFocusManager.current
    var passwordVisibility by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier
                .border(
                    width =  1.dp,
                    color = if(textFieldErrorMessage == null ){
                        MaterialTheme.colors.onBackground
                    } else MaterialTheme.colors.error
                )
                .size(size)
                .background(Color.White),
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
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier.padding(horizontal = size.height/3),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
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
                    if(keyboardType == KeyboardType.Password){
                        IconButton(
                            modifier = Modifier.size(size.height/2),
                            onClick = { passwordVisibility = !passwordVisibility }
                        ) {
                            Icon(
                                imageVector = if (passwordVisibility){
                                    Icons.Filled.Visibility
                                }else Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                }
            },
            visualTransformation =
                if(keyboardType == KeyboardType.Password){
                    if (passwordVisibility){
                        VisualTransformation.None
                    }else{
                        PasswordVisualTransformation()
                    }
                } else VisualTransformation.None,
        )
        if(textFieldErrorMessage != null){
            Row(
                modifier = Modifier
                    .size(width = size.width/4, size.height)
                    .offset(x = size.width/2 + size.width/8)
                    .padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    modifier = Modifier.size(size.width/20),
                    imageVector = Icons.Default.ErrorOutline,
                    tint = MaterialTheme.colors.error,
                    contentDescription = null
                )
                Text(
                    text = textFieldErrorMessage,
                    style = MaterialTheme.typography.overline.copy(fontSize = 8.sp),
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}
