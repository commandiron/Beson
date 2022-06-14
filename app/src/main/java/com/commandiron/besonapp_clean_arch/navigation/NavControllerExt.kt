package com.commandiron.besonapp_clean_arch.navigation

import androidx.navigation.NavController

fun NavController.navigateWithNavigationOptions(navigationOptions: NavigationOptions){
    if(navigationOptions.popBackStack){
        this.popBackStack()
    }
    this.navigate(navigationOptions.route){
        navigationOptions.popUpToRoute?.let {
            popUpTo(it){
                inclusive = navigationOptions.inclusive
            }
        }
        launchSingleTop = navigationOptions.launchSingleTop
    }
}