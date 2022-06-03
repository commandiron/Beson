package com.commandiron.besonapp_clean_arch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.commandiron.besonapp_clean_arch.navigation.*
import com.commandiron.besonapp_clean_arch.presentation.components.AddPriceButton
import com.commandiron.besonapp_clean_arch.presentation.components.AppTopBar
import com.commandiron.besonapp_clean_arch.presentation.components.CustomSnackBar
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepVisibleCondition{
            viewModel.state.isColdSplashScreenVisible
        }
        setContent {
            BesonApp_Clean_ArchTheme {
                val multiplePermissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        //if permission is required.
                    )
                )
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)
                val coroutineScope = rememberCoroutineScope()
                val systemUiController = rememberSystemUiController()
                val scaffoldState = rememberScaffoldState()
                val snackbarHostState = scaffoldState.snackbarHostState
                CompositionLocalProvider(
                    values = getProvidedValues(
                        multiplePermissionsState = multiplePermissionsState,
                        navController = navController,
                        coroutineScope = coroutineScope,
                        systemUiController = systemUiController,
                        snackbarHostState = snackbarHostState
                    )
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        scaffoldState = scaffoldState,
                        topBar = { AppTopBar() },
                        floatingActionButton = {
                            AddPriceButton(
                                onClick = {navController.navigate(NavigationItem.PostPrice.route)}
                            )
                        },
                        floatingActionButtonPosition = FabPosition.Center,
                        isFloatingActionButtonDocked = true,
                        bottomBar ={ AppBottomNavigation() },
                        snackbarHost = {
                            SnackbarHost(hostState = it){ data ->
                                CustomSnackBar(data)
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.background
                    ) {
                        ModalBottomSheetLayout(
                            bottomSheetNavigator = bottomSheetNavigator,
                            sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        ) {
                            NavigationHost(
                                shouldShowSplashAndIntro = viewModel
                                    .state
                                    .shouldShowSplashAndIntro
                            )
                        }
                    }
                }
            }
        }
    }
}