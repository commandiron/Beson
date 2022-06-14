package com.commandiron.besonapp_clean_arch.presentation.prices.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.MyPurple
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PriceMenuItem(
    modifier: Modifier = Modifier,
    item: PriceItem,
    onDetailClick:(PriceItem) -> Unit
) {
    val spacing = LocalSpacing.current
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        border = BorderStroke(1.dp, MyPurple)
    ) {
        Box{
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight(),
                    color = MyPurple,
                    contentColor = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.padding(spacing.spaceMedium)) {
                        val sdf = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT) }
                        Text(
                            text = sdf.format(item.date),
                            style = MaterialTheme.typography.labelMedium,
                        )
                        Text(text = item.location)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .padding(horizontal = spacing.spaceMedium),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${item.price} ${Strings.CURRENCY_SYMBOL}/${item.unit}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Icon(
                    modifier = Modifier
                        .weight(2f)
                        .rotate(90f)
                        .clickable { onDetailClick(item) },
                    imageVector = Icons.Default.ArrowCircleUp,
                    contentDescription = null,
                    tint = NoErrorGreen
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(vertical = spacing.spaceSmall, horizontal = spacing.spaceMedium),
                text = item.userByName,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                textAlign = TextAlign.Center
            )
        }
    }
}