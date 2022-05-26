package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.core.Strings.BESON

@Composable
fun LogoWithAppName(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppLogoIcon(modifier = modifier.size(30.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = BESON,
            modifier = Modifier.align(Alignment.Bottom),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
        )
    }
}