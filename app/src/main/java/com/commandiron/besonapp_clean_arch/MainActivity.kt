package com.commandiron.besonapp_clean_arch

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.commandiron.besonapp_clean_arch.core.Strings.SNACKBAR_HIDE_ACTION_TEXT
import com.commandiron.besonapp_clean_arch.navigation.*
import com.commandiron.besonapp_clean_arch.presentation.components.AddPriceButton
import com.commandiron.besonapp_clean_arch.presentation.components.AppTopBar
import com.commandiron.besonapp_clean_arch.presentation.components.CustomSnackBar
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.LinearProgressLoadingDialog
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)
                val systemUiController = rememberSystemUiController()
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val snackbarHostState = scaffoldState.snackbarHostState
                val keyboardController = LocalSoftwareKeyboardController.current
                var isLoading by remember { mutableStateOf(false)}
                var loadingMessage by remember { mutableStateOf("")}
                CompositionLocalProvider(
                    values = getProvidedValues(
                        permissionsState = permissionsState,
                        navController = navController,
                        systemUiController = systemUiController
                    )
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        scaffoldState = scaffoldState,
                        topBar = { AppTopBar() },
                        floatingActionButton = {
                            AddPriceButton(
                                onClick = {
                                    navController.navigate(NavigationItem.PostPrice.route)
                                    permissionsState.launchMultiplePermissionRequest()
                                }
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
                                    .shouldShowSplashAndIntro,
                                hideKeyboard = { keyboardController?.hide() },
                                navigateUp = { navController.navigateUp() },
                                navigateTo = { navController.navigate(it) },
                                showSnackbar = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(it, SNACKBAR_HIDE_ACTION_TEXT)
                                    }
                                },
                                showLoadingScreen = {
                                    isLoading = true
                                    loadingMessage = it
                                },
                                hideLoadingScreen = { isLoading = false }
                            )
                            if(isLoading){
                                LinearProgressLoadingDialog(loadingMessage)
                            }
                            permissionsState.permissions.forEach { perm ->
                                when(perm.permission){
                                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                                        when(perm.status){
                                            PermissionStatus.Granted -> {
                                            }
                                            is PermissionStatus.Denied -> {
                                                if(perm.status.shouldShowRationale){
                                                    //Kullanıcı kalıcı olarak kabul etmedi,
                                                    //gerekçesini sun ve ayarlardan açmasını iste.
                                                }else{
                                                    //Kullanıcı henüz cevap vermedi.
                                                }
                                            }
                                        }
                                    }
                                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                        when(perm.status){
                                            PermissionStatus.Granted -> {
                                            }
                                            is PermissionStatus.Denied -> {

                                                if(perm.status.shouldShowRationale){
                                                    //Kullanıcı kalıcı olarak kabul etmedi,
                                                    //gerekçesini sun ve ayarlardan açmasını iste.
                                                }else{
                                                    //Kullanıcı henüz cevap vermedi.
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}