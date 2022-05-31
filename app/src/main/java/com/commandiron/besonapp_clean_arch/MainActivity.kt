package com.commandiron.besonapp_clean_arch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.*
import com.commandiron.besonapp_clean_arch.presentation.components.AddPriceButton
import com.commandiron.besonapp_clean_arch.presentation.components.AppTopBar
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    @Inject
    lateinit var useCases: UseCases

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepVisibleCondition{
            splashViewModel.isColdSplashScreenVisible.value
        }
        val shouldShowSplashAndIntro = useCases.loadShouldShowSplashAndIntro()
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
                CompositionLocalProvider(
                    values = getProvidedValues(
                        multiplePermissionsState = multiplePermissionsState,
                        navController = navController,
                        coroutineScope = coroutineScope,
                        systemUiController = systemUiController
                    )
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        topBar = { AppTopBar() },
                        floatingActionButton = {
                            AddPriceButton(
                                onClick = {navController.navigate(NavigationItem.PostPrice.route)}
                            )
                        },
                        floatingActionButtonPosition = FabPosition.Center,
                        isFloatingActionButtonDocked = true,
                        bottomBar ={ AppBottomNavigation() },
                        backgroundColor = MaterialTheme.colorScheme.background
                    ) {
                        ModalBottomSheetLayout(
                            bottomSheetNavigator = bottomSheetNavigator,
                            sheetShape = RoundedCornerShape(32.dp)
                        ) {
                            NavigationHost(shouldShowSplashAndIntro = shouldShowSplashAndIntro)
                        }
                    }
                }
            }
        }
    }
}