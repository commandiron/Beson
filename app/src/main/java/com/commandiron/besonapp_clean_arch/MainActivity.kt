package com.commandiron.besonapp_clean_arch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.navigation.*
import com.commandiron.besonapp_clean_arch.presentation.components.AddPriceButton
import com.commandiron.besonapp_clean_arch.presentation.components.AppTopBar
import com.commandiron.besonapp_clean_arch.ui.theme.*
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
            splashViewModel.isColdSplashScreenVisible.value
        }
        val shouldShowSplashAndIntro = preferences.loadShouldShowSplashAndIntro()
        setContent {
            BesonApp_Clean_ArchTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val systemUiController = rememberSystemUiController()
                CompositionLocalProvider(
                    values = getProvidedValues(
                        navController = navController,
                        coroutineScope = coroutineScope,
                        scaffoldState = scaffoldState,
                        systemUiController = systemUiController
                    )
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        scaffoldState = scaffoldState,
                        topBar = { AppTopBar() },
                        floatingActionButton = { AddPriceButton(onClick = {}) },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center,
                        bottomBar ={ AppBottomNavigation() }
                    ) {
                        NavigationHost(shouldShowSplashAndIntro = shouldShowSplashAndIntro)
                    }
                }
            }
        }
    }
}