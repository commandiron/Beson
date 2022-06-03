package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Constraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.COMPANY_STATEMENT_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.CUSTOMER_STATEMENT_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_UPPERCASE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SNACKBAR_HIDE_ACTION_TEXT
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatableSignUpWindow
import com.commandiron.besonapp_clean_arch.presentation.components.AnimatedAppExplainingStrip
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.LinearProgressLoadingDialog
import com.commandiron.besonapp_clean_arch.presentation.signup.components.CustomLogInButton
import com.commandiron.besonapp_clean_arch.presentation.signup.components.SignUpForm
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.example.besonapp.presentation.SignUpScreenLogoAnimation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = LocalCoroutineScope.current
    val systemUiController = LocalSystemUiController.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                UiEvent.HideKeyboard -> {
                    keyboardController?.hide()
                }
                is UiEvent.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message, SNACKBAR_HIDE_ACTION_TEXT)
                    }
                }
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.tertiary
    )
    val placeholderModifier = Modifier.placeholder(
        visible = state.isLoading,
        highlight = PlaceholderHighlight.shimmer()
    )
    BoxWithConstraints {
        SignUpScreenBackground(
            modifier = Modifier.fillMaxSize(),
            placeholderModifier = placeholderModifier,
            state = state,
            viewModel = viewModel
        )
        SignUpScreenForeground(
            state = state,
            viewModel = viewModel,
            constraints = constraints
        )
    }
    if(state.isLoading){
        LinearProgressLoadingDialog(title = state.loadingMessage)
    }
}

@Composable
fun SignUpScreenBackground(
    modifier: Modifier = Modifier,
    placeholderModifier: Modifier = Modifier,
    state: SignUpState,
    viewModel: SignUpViewModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SignUpForm(
            placeholderModifier = placeholderModifier,
            registrationFormState = state.registrationFormState,
            buttonText = SIGN_UP_AS_CUSTOMER_TEXT,
            onEmailChanged = {
                viewModel.onEvent(SignUpUserEvent.EmailChanged(it))
            },
            onPasswordChanged = {
                viewModel.onEvent(SignUpUserEvent.PasswordChanged(it))
            },
            onRepeatedPasswordChanged = {
                viewModel.onEvent(SignUpUserEvent.RepeatedPasswordChanged(it))
            },
            onSignUpButtonClick = {
                viewModel.onEvent(SignUpUserEvent.OnSignUpUserAsCustomerClick)
            }
        )
        SignUpForm(
            placeholderModifier = placeholderModifier,
            registrationFormState = state.registrationFormState,
            buttonText = SIGN_UP_AS_COMPANY_TEXT,
            onEmailChanged = {
                viewModel.onEvent(SignUpUserEvent.EmailChanged(it))
            },
            onPasswordChanged = {
                viewModel.onEvent(SignUpUserEvent.PasswordChanged(it))
            },
            onRepeatedPasswordChanged = {
                viewModel.onEvent(SignUpUserEvent.RepeatedPasswordChanged(it))
            },
            onSignUpButtonClick = {
                viewModel.onEvent(SignUpUserEvent.OnSignUpUserAsCompanyClick)
            }
        )
    }
}

@Composable
fun SignUpScreenForeground(
    modifier: Modifier = Modifier,
    placeholderModifier: Modifier = Modifier,
    state: SignUpState,
    viewModel: SignUpViewModel,
    constraints: Constraints
) {
    val spacing = LocalSpacing.current
    Column(modifier) {
        AnimatableSignUpWindow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            placeholderModifier = placeholderModifier,
            title =  I_AM_CUSTOMER_TEXT,
            details = CUSTOMER_STATEMENT_TEXT,
            buttonText = SIGNUP_UPPERCASE_TEXT,
            backgroundImageUrl = state.customerWindowsBackgroundUrl,
            surfaceColor = SignUpCustomerBackgroundColor,
            targetOffsetValue = -constraints.maxHeight/4.toFloat() + 130f,
            isUiWindowOpen = state.isCustomerUiWindowOpen,
            onButtonClick = {
                viewModel.onEvent(SignUpUserEvent.OnCustomerWindowSignUpUserClick)
            }
        )
        AnimatableSignUpWindow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            placeholderModifier = placeholderModifier,
            title =  I_AM_COMPANY_TEXT,
            details = COMPANY_STATEMENT_TEXT,
            buttonText = SIGNUP_UPPERCASE_TEXT,
            backgroundImageUrl = state.companyWindowsBackgroundUrl,
            surfaceColor = SignUpCompanyBackgroundColor,
            targetOffsetValue = constraints.maxHeight/4.toFloat() - 130f,
            isUiWindowOpen = state.isCompanyUiWindowOpen,
            onButtonClick = {
                viewModel.onEvent(SignUpUserEvent.OnCompanyWindowSignUpUserClick)
            }
        )
    }
    BoxWithConstraints {
        SignUpScreenLogoAnimation(
            placeholderModifier = placeholderModifier,
            onSignUpScreenLogoClick = {
                viewModel.onEvent(SignUpUserEvent.OnLogoClick)
            }
        )
        CustomLogInButton(
            placeholderModifier = placeholderModifier,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = spacing.spaceMedium),
            text = SIGN_IN_TEXT,
            onClick = {
                viewModel.onEvent(SignUpUserEvent.OnSignInClick)
            }
        )
        AnimatedAppExplainingStrip(
            placeholderModifier = placeholderModifier,
            screenSize = Size(
                width = constraints.maxWidth.toFloat(),
                height = constraints.maxHeight.toFloat()
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.spaceLarge),
            isAnimated = true
        )
    }
}

























