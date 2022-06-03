package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.presentation.profile.model.MyUpdates
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun MyUpdatesMenuItem(
    item: MyUpdates,
    onDelete:(Int) -> Unit
) {
    val spacing = LocalSpacing.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = item.title
                )
                Row {
                    Text(text = item.date)
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    Text(text = item.location)
                }
            }
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.price} ${Strings.CURRENCY_SYMBOL}/${item.unit}"
            )
            Icon(
                modifier = Modifier.clickable { onDelete(item.itemId) },
                imageVector = Icons.Default.DeleteForever,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}