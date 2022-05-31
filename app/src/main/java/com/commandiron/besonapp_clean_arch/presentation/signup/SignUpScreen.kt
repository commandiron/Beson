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
import com.commandiron.besonapp_clean_arch.core.Strings.LOGIN_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_UPPERCASE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatableSignUpWindow
import com.commandiron.besonapp_clean_arch.presentation.components.AnimatedAppExplainingStrip
import com.commandiron.besonapp_clean_arch.presentation.signup.components.CustomLogInButton
import com.commandiron.besonapp_clean_arch.presentation.signup.components.SignUpForm
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpUiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpUserEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.state.SignUpState
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.example.besonapp.presentation.SignUpScreenLogoAnimation
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val coroutineScope = LocalCoroutineScope.current
    val systemUiController = LocalSystemUiController.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is SignUpUiEvent.SignUpValidationAndFirebaseRegisterSuccessAsCustomer -> {
                    navController.navigate(
                        NavigationItem.SignUpStepsAsCustomer1.route
                    )
                    keyboardController?.hide()
                }
                is SignUpUiEvent.SignUpValidationAndFirebaseRegisterSuccessAsCompany -> {
                    navController.navigate(
                        NavigationItem.SignUpStepsAsCompany1.route
                    )
                    keyboardController?.hide()
                }
                is SignUpUiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        //snackbar uygulanacak
                    }
                }
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.tertiary
    )
    BoxWithConstraints {
        SignUpScreenBackground(
            state = state,
            viewModel = viewModel
        )
        SignUpScreenForeground(
            state = state,
            viewModel = viewModel,
            constraints = constraints,
            onLogInClick = {
                navController.popBackStack()
                navController.navigate(
                    NavigationItem.LogIn.route
                )
            }
        )
    }
}

@Composable
fun SignUpScreenBackground(
    state: SignUpState,
    viewModel: SignUpViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SignUpForm(
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
    state: SignUpState,
    viewModel: SignUpViewModel,
    constraints: Constraints,
    onLogInClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column {
        AnimatableSignUpWindow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
            onSignUpScreenLogoClick = {
                viewModel.onEvent(SignUpUserEvent.OnLogoClick)
            }
        )
        CustomLogInButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = spacing.spaceMedium),
            text = LOGIN_TEXT,
            onClick = {
                viewModel.onEvent(SignUpUserEvent.OnLogInClick)
                onLogInClick()
            }
        )
        AnimatedAppExplainingStrip(
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

























