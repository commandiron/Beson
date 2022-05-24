package com.commandiron.besonapp_clean_arch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.commandiron.besonapp_clean_arch.presentation.intro.IntroScreen
import com.commandiron.besonapp_clean_arch.presentation.login.LogInScreen
import com.commandiron.besonapp_clean_arch.presentation.signup.SignUpScreen
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsCompany1
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsCustomer1
import com.commandiron.besonapp_clean_arch.presentation.splash.HotSplashScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    shouldShowSplashAndIntro: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(shouldShowSplashAndIntro) {
            Route.HOT_SPLASH
        } else Route.SIGN_UP
    ){
        composable(Route.HOT_SPLASH){
            HotSplashScreen(
                onFinish = {
                    navController.navigate(
                        Route.INTRO
                    )
                }
            )
        }
        composable(Route.INTRO){
            IntroScreen(
                onNextClick = {
                    navController.navigate(
                        Route.SIGN_UP
                    )
                }
            )
        }
        composable(Route.SIGN_UP){
            SignUpScreen(
                onSignUpAsCustomerClick = {
                    navController.navigate(
                        Route.SIGN_UP_STEPS_CUSTOMER
                    )
                },
                onSignUpAsCompanyClick = {
                    navController.navigate(
                        Route.SIGN_UP_STEPS_COMPANY
                    )
                },
                onLogInClick = {
                    navController.navigate(
                        Route.LOG_IN
                    )
                }
            )
        }
        composable(Route.LOG_IN){
            LogInScreen()
        }
        composable(Route.SIGN_UP_STEPS_CUSTOMER){
            SignUpStepsCustomer1()
        }
        composable(Route.SIGN_UP_STEPS_COMPANY){
            SignUpStepsCompany1()
        }
    }
}