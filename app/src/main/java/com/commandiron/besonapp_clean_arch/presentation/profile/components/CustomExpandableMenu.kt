package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun CustomExpandableMenu(

    title:String,
    isExpanded: Boolean,
    onDropDownIconClick:() -> Unit,
    expandedContent: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    delayMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .fillMaxWidth()
            .padding(horizontal = spacing.spaceMedium),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = spacing.spaceMedium,
                    bottom = spacing.spaceMedium,
                    start = spacing.spaceMedium
                )
        ) {
            ExpandableMenuTitle(
                title = title,
                isExpanded = isExpanded,
                onDropDownIconClick = onDropDownIconClick
            )
            if(isExpanded){
                expandedContent()
            }
        }

    }
}