package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.commandiron.besonapp_clean_arch.presentation.model.ConstructionItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    iconPadding: Dp = 24.dp,
    itemMain: ConstructionItem,
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.padding(iconPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                painter = rememberAsyncImagePainter(model = itemMain.imageResource),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                text = itemMain.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall
            )
        }
        content()
    }
}