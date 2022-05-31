package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.navigation.currentRoute
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun AddPriceButton(
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val currentRoute = navController.currentRoute()
    val buttonVisibleState = remember {
        MutableTransitionState(false)
    }
    val navigationItems = listOf(
        NavigationItem.Profile,
        NavigationItem.Prices
    )
    val buttonVisibleRouteList = navigationItems.map { it.route }
    LaunchedEffect(key1 = currentRoute){
        buttonVisibleState.targetState = buttonVisibleRouteList.contains(currentRoute)
    }
    AnimatedVisibility(
        visibleState = buttonVisibleState,
        enter = scaleIn(tween(durationMillis = 250)),
        exit = scaleOut(tween(durationMillis = 250))
    ) {
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(4.dp).copy(CornerSize(percent = 50)),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = spacing.defaultElevation
        ) {
            Box(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = 56.dp,
                        minHeight = 56.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        }
    }
}