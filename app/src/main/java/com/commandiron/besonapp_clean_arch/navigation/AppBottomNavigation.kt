package com.commandiron.besonapp_clean_arch.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController

@Composable
fun AppBottomNavigation() {
    val navController = LocalNavController.current
    val currentRoute = navController.currentRoute()
    val bottomNavigationVisibleState = remember {
        MutableTransitionState(false)
    }
    val navigationItems = listOf(
        NavigationItem.Profile,
        NavigationItem.Prices
    )
    val bottomNavigationVisibleRouteList = navigationItems.map { it.route }
    LaunchedEffect(key1 = currentRoute){
        bottomNavigationVisibleState.targetState = bottomNavigationVisibleRouteList.contains(currentRoute)
    }
    AnimatedVisibility(
        visibleState = bottomNavigationVisibleState,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp
        ) {
            navigationItems.forEach { item ->
                BottomNavigationItem(
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
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.overline
                        )
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    unselectedContentColor = MaterialTheme.colors.onBackground.copy(0.5f),
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
                    }
                )
            }
        }
    }
}