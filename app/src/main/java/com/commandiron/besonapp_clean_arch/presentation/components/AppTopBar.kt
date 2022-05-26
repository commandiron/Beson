package com.commandiron.besonapp_clean_arch.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun AppTopBar(){
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val currentRoute = navController.currentRoute()
    val topBarVisibleState = remember { MutableTransitionState(false) }
    val navigationItems = listOf(
        NavigationItem.Profile,
        NavigationItem.EditProfile,
        NavigationItem.MyPriceUpdates
    )
    val topBarVisibleRouteList = navigationItems.map { it.route }
    LaunchedEffect(key1 = currentRoute){
        topBarVisibleState.targetState = topBarVisibleRouteList.contains(currentRoute)
    }
    AnimatedVisibility(
        visibleState = topBarVisibleState,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        TopAppBar(
            modifier = Modifier.height(spacing.topBarHeight)
        ){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                navigationItems.forEach { item ->
                    if(item.route == currentRoute){
                        Text(
                            text =  item.title ?: "Title",
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}