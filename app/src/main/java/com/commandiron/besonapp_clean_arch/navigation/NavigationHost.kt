package com.commandiron.besonapp_clean_arch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.commandiron.besonapp_clean_arch.presentation.edit_profile.EditProfileScreen
import com.commandiron.besonapp_clean_arch.presentation.intro.IntroScreen
import com.commandiron.besonapp_clean_arch.presentation.login.LogInScreen
import com.commandiron.besonapp_clean_arch.presentation.signup.SignUpScreen
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpSteps1
import com.commandiron.besonapp_clean_arch.presentation.hot_splash.HotSplashScreen
import com.commandiron.besonapp_clean_arch.presentation.my_price_updates.MyPriceUpdatesScreen
import com.commandiron.besonapp_clean_arch.presentation.prices.PricesScreen
import com.commandiron.besonapp_clean_arch.presentation.profile.ProfileScreen
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController

@Composable
fun NavigationHost(
    shouldShowSplashAndIntro: Boolean
) {
    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = if(shouldShowSplashAndIntro) {
            NavigationItem.HotSplash.route
        } else NavigationItem.Profile.route
    ){
        composable(NavigationItem.HotSplash.route){
            HotSplashScreen()
        }
        composable(NavigationItem.Intro.route){
            IntroScreen()
        }
        composable(NavigationItem.SignUp.route){
            SignUpScreen()
        }
        composable(NavigationItem.LogIn.route){
            LogInScreen()
        }
        composable(NavigationItem.SignUpSteps1.route){
            SignUpSteps1()
        }
        composable(NavigationItem.Profile.route){
            ProfileScreen()
        }
        composable(NavigationItem.EditProfile.route){
            EditProfileScreen()
        }
        composable(NavigationItem.MyPriceUpdates.route){
            MyPriceUpdatesScreen()
        }
        composable(NavigationItem.Prices.route){
            PricesScreen()
        }
    }
}