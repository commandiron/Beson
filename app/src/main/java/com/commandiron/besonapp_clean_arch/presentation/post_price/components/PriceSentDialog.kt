package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun PriceSentDialog(
    onDismissRequest:() -> Unit
) {
    val spacing = LocalSpacing.current
    Dialog(onDismissRequest = onDismissRequest) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Fiyat Gönderildi",
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}