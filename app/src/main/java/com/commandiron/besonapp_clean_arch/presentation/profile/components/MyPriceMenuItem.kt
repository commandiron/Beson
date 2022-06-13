package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.MyPurple
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyPriceMenuItem(
    item: PriceItem,
    onDelete:(PriceItem) -> Unit
) {
    val spacing = LocalSpacing.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                color = MyPurple,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                Column(Modifier.padding(spacing.spaceMedium)) {
                    val sdf = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT) }
                    Text(
                        text = sdf.format(item.date),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(text = item.location)
                }
            }
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = spacing.spaceMedium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${item.price} ${Strings.CURRENCY_SYMBOL}/${item.unit}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(spacing.spaceSmall)
                    .clickable { onDelete(item) },
                imageVector = Icons.Default.DeleteForever,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}