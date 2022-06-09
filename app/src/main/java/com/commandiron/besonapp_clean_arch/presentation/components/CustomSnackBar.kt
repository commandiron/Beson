package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackBar(
    data : SnackbarData
) {
    Snackbar(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(horizontal = 40.dp),
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.tertiary,
        actionColor = MaterialTheme.colorScheme.tertiary,
        snackbarData = data
    )
}