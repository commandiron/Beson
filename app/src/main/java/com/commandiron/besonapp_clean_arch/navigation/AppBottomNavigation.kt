package com.commandiron.besonapp_clean_arch.navigation

import androidx.compose.animation.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController

@Composable
fun AppBottomNavigation() {
    val navController = LocalNavController.current
    val currentRoute = navController.currentRoute()
    val bottomNavigationVisibleNavigationItemsList = NavigationItem.navigationItems
        .filter { it.isBottomNavigationVisible }
    val bottomNavigationIsVisible = bottomNavigationVisibleNavigationItemsList
        .map { it.route }
        .contains(currentRoute)
    AnimatedVisibility(
        visible = bottomNavigationIsVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background
        ) {
            bottomNavigationVisibleNavigationItemsList.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = rememberAsyncImagePainter(
                                model = item.iconResource
                            ),
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title ?: "Navigation Item Title",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    alwaysShowLabel = false,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}