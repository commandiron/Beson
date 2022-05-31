package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
        CenterAlignedTopAppBar(
            modifier = Modifier.height(spacing.topBarHeight),
            title = {
                Text(
                    text =  NavigationItem.fromRouteString(currentRoute).title ?: "",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}