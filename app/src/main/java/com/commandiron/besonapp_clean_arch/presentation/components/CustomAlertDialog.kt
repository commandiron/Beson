package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.core.Strings.NO
import com.commandiron.besonapp_clean_arch.core.Strings.YES
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen

@Composable
fun CustomAlertDialog(
    priceText: String = "",
    title: String,
    onDismissRequest:() -> Unit,
    onConfirm:() -> Unit,
    onDismiss:() -> Unit,
) {
    val spacing  = LocalSpacing.current
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onConfirm,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = spacing.defaultElevation
                )
            ) {
                Text(text = YES)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = NO)
            }
        },
        title = {
            Column() {
                Text(
                    text = priceText,
                    color = NoErrorGreen,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        shape = RoundedCornerShape(30.dp),
        containerColor = MaterialTheme.colorScheme.background
    )
}