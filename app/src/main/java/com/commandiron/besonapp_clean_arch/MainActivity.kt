package com.commandiron.besonapp_clean_arch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commandiron.besonapp_clean_arch.navigation.Route
import com.commandiron.besonapp_clean_arch.presentation.intro.IntroScreen
import com.commandiron.besonapp_clean_arch.presentation.splash.SplashScreen
import com.commandiron.besonapp_clean_arch.presentation.splash.SplashViewModel
import com.commandiron.besonapp_clean_arch.ui.theme.BesonApp_Clean_ArchTheme
import com.commandiron.besonapp_clean_arch.ui.theme.backgroundDarkColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepVisibleCondition{
            splashViewModel.isSplashScreenVisible.value
        }
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
                        startDestination = Route.SPLASH
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
                                    //TO SIGNUP SCREEN
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}