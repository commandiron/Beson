package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun LinearProgressLoadingDialog(
    title: String
) {
    val spacing = LocalSpacing.current
    Dialog(onDismissRequest = {}) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = androidx.compose.ui.Modifier.height(spacing.spaceSmall))
            LinearProgressIndicator()
        }
    }
}