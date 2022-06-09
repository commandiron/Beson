package com.commandiron.besonapp_clean_arch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.commandiron.besonapp_clean_arch.presentation.customer_or_company.CustomerOrCompanyScreen
import com.commandiron.besonapp_clean_arch.presentation.edit_profile.EditProfileScreen
import com.commandiron.besonapp_clean_arch.presentation.intro.IntroScreen
import com.commandiron.besonapp_clean_arch.presentation.signin.SignInScreen
import com.commandiron.besonapp_clean_arch.presentation.signup.SignUpScreen
import com.commandiron.besonapp_clean_arch.presentation.hot_splash.HotSplashScreen
import com.commandiron.besonapp_clean_arch.presentation.post_price.PostPriceScreen
import com.commandiron.besonapp_clean_arch.presentation.prices.PricesScreen
import com.commandiron.besonapp_clean_arch.presentation.profile.ProfileScreen
import com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company.*
import com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer.SignUpStepsAsCustomerScreen1
import com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer.SignUpStepsAsCustomerScreen2
import com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer.SignUpStepsAsCustomerScreen3
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.google.accompanist.navigation.material.bottomSheet

@Composable
fun NavigationHost(
    shouldShowSplashAndIntro: Boolean,
    hideKeyboard:() -> Unit,
    navigateUp:() -> Unit,
    navigateTo:(String) -> Unit,
    showSnackbar:(String) -> Unit,
    showLoadingScreen:(String) -> Unit,
    hideLoadingScreen:() -> Unit
) {
    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination =
        when {
            shouldShowSplashAndIntro -> {
                NavigationItem.HotSplash.route
            }
            else -> {
                NavigationItem.SignUp.route
            }
        }
    ){
        composable(NavigationItem.HotSplash.route){
            HotSplashScreen(onFinish = { navigateTo(NavigationItem.Intro.route) })
        }
        composable(NavigationItem.Intro.route){
            IntroScreen(navigateTo = navigateTo)
        }
        composable(NavigationItem.SignUp.route){
            SignUpScreen(
                hideKeyboard = hideKeyboard,
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.SignIn.route){
            SignInScreen(
                hideKeyboard = hideKeyboard,
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.CustomerOrCompany.route){
            CustomerOrCompanyScreen(
                navigateTo = navigateTo,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.SignUpStepsAsCustomer1.route){
            SignUpStepsAsCustomerScreen1(navigateTo = navigateTo)
        }
        composable(NavigationItem.SignUpStepsAsCustomer2.route){
            SignUpStepsAsCustomerScreen2(navigateTo = navigateTo)
        }
        composable(NavigationItem.SignUpStepsAsCustomer3.route){
            SignUpStepsAsCustomerScreen3(
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.SignUpStepsAsCompany1.route){
            SignUpStepsAsCompanyScreen1(
                navigateUp = navigateUp,
                navigateTo = navigateTo
            )
        }
        composable(NavigationItem.SignUpStepsAsCompany2.route){
            SignUpStepsAsCompanyScreen2(navigateTo = navigateTo)
        }
        composable(NavigationItem.SignUpStepsAsCompany3.route){
            SignUpStepsAsCompanyScreen3(navigateTo = navigateTo)
        }
        composable(NavigationItem.SignUpStepsAsCompany4.route){
            SignUpStepsAsCompanyScreen4(navigateTo = navigateTo)
        }
        composable(NavigationItem.SignUpStepsAsCompany5.route){
            SignUpStepsAsCompanyScreen5(
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.Profile.route){
            ProfileScreen(
                hideKeyboard = hideKeyboard,
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.EditProfile.route){
            EditProfileScreen(
                hideKeyboard = hideKeyboard,
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
        composable(NavigationItem.Prices.route){
            PricesScreen()
        }
        bottomSheet(NavigationItem.PostPrice.route){
            PostPriceScreen(
                hideKeyboard = hideKeyboard,
                navigateTo = navigateTo,
                showLoadingScreen = showLoadingScreen,
                hideLoadingScreen = hideLoadingScreen,
                showSnackbar = showSnackbar
            )
        }
    }
}