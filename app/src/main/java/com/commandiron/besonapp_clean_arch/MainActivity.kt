package com.commandiron.besonapp_clean_arch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.navigation.Route
import com.commandiron.besonapp_clean_arch.presentation.intro.IntroScreen
import com.commandiron.besonapp_clean_arch.presentation.login.LogInScreen
import com.commandiron.besonapp_clean_arch.presentation.signup.SignUpScreen
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsCompany1
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsCustomer1
import com.commandiron.besonapp_clean_arch.presentation.splash.SplashScreen
import com.commandiron.besonapp_clean_arch.presentation.splash.SplashViewModel
import com.commandiron.besonapp_clean_arch.ui.theme.BesonApp_Clean_ArchTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    @Inject
    lateinit var preferences: Preferences

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepVisibleCondition{
            splashViewModel.isSplashScreenVisible.value
        }
        val shouldShowSplashAndIntro = preferences.loadShouldShowSplashAndIntro()
        setContent {
            BesonApp_Clean_ArchTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val systemUiController = rememberSystemUiController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if(shouldShowSplashAndIntro) {
                            Route.SPLASH
                        } else Route.SIGN_UP
                    ){
                        composable(Route.SPLASH){
                            SplashScreen(
                                onFinish = {
                                    navController.navigate(
                                        Route.INTRO
                                    )
                                }
                            )
                        }
                        composable(Route.INTRO){
                            IntroScreen(
                                systemUiController = systemUiController,
                                onNextClick = {
                                    navController.navigate(
                                        Route.SIGN_UP
                                    )
                                }
                            )
                        }
                        composable(Route.SIGN_UP){
                            SignUpScreen(
                                systemUiController = systemUiController,
                                onCustomerSignUpClick = {
                                    navController.navigate(
                                        Route.SIGN_UP_STEPS_CUSTOMER
                                    )
                                },
                                onCompanySignUpClick = {
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
            }
        }
    }
}