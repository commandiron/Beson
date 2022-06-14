package com.commandiron.besonapp_clean_arch.navigation

data class NavigationOptions(
    val route: String,
    val popBackStack: Boolean = false,
    val popUpToRoute: String? = null,
    val inclusive: Boolean = false,
    val launchSingleTop: Boolean = false,
)