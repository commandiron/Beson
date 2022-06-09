package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun PriceTextField(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Unspecified,
    onClick:() -> Unit,
    value: String,
    onValueChange:(String) -> Unit,
    enabled: Boolean,
    onDone:(KeyboardActionScope) -> Unit,
    addedSymbol: String
) {
    Surface(
        modifier = modifier.border(1.dp, borderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                modifier = Modifier.clickable {
                    onClick()
                },
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onDone = onDone),
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Start
                ),
                visualTransformation = ThousandSeparatorVisualTransformationWithAddedSymbol(
                    maxFractionDigits = 2,
                    addedSymbol = addedSymbol
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                        if(value.isEmpty()){
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.height(IntrinsicSize.Min)
                            )
                        }
                    }
                }
            )
        }
    }
}