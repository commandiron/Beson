package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun ExpandableMenuTitle(
    title: String,
    isExpanded: Boolean,
    onDropDownIconClick:() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(6f),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Icon(
            modifier = Modifier
                .weight(1f)
                .rotate(if (isExpanded) 180f else 0f)
                .clickable { onDropDownIconClick() },
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
        )
    }
}