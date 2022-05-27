package com.commandiron.besonapp_clean_arch.presentation.components

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
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem.Companion.navigationItems
import com.commandiron.besonapp_clean_arch.navigation.currentRoute
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun AppTopBar(){
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val currentRoute = navController.currentRoute()
    val topBarVisibleNavigationItemsList = navigationItems.filter { it.isTopBarVisible }
    val topBarIsVisible = topBarVisibleNavigationItemsList.map { it.route }.contains(currentRoute)
    AnimatedVisibility(
        visible = topBarIsVisible,
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
                Text(
                    text =  NavigationItem.fromRouteString(currentRoute).title ?: "Title",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}