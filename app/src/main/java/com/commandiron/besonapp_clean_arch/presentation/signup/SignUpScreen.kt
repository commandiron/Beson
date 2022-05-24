package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
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
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_SCREEN_COMPANY_IMAGE_URL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_SCREEN_CUSTOMER_IMAGE_URL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_UPPERCASE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatableSignUpWindow
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatedAppExplainingStrip
import com.commandiron.besonapp_clean_arch.presentation.signup.components.CustomLogInButton
import com.commandiron.besonapp_clean_arch.presentation.signup.components.SignUpForm
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.state.SignUpState
import com.commandiron.besonapp_clean_arch.presentation.signup.state.UserType
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.example.besonapp.presentation.SignUpScreenLogoAnimation
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onSignUpAsCustomerClick:() -> Unit,
    onSignUpAsCompanyClick:() -> Unit,
    onLogInClick: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val coroutineScope = LocalCoroutineScope.current
    val snackbarHostState = LocalSnackbarHostState.current
    val systemUiController = LocalSystemUiController.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.Success -> {
                    when(state.userType){
                        UserType.CUSTOMER -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = SIGN_UP_SUCCESSFUL
                                )
                            }
                            onSignUpAsCustomerClick()
                            keyboardController?.hide()
                        }
                        UserType.COMPANY -> {
                            onSignUpAsCompanyClick()
                            keyboardController?.hide()
                        }
                    }
                }
                else -> Unit
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.onBackground
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
            onLogInClick = onLogInClick
        )
    }
}


@Composable
fun SignUpScreenBackground(
    state: SignUpState,
    viewModel: SignUpViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(2){ item ->
            SignUpForm(
                registrationFormState = state.registrationFormState,
                buttonText = if(item == 0) SIGN_UP_AS_CUSTOMER_TEXT else SIGN_UP_AS_COMPANY_TEXT,
                onEmailChanged = {
                    viewModel.onEvent(SignUpEvent.EmailChanged(it))
                },
                onPasswordChanged = {
                    viewModel.onEvent(SignUpEvent.PasswordChanged(it))
                },
                onRepeatedPasswordChanged = {
                    viewModel.onEvent(SignUpEvent.RepeatedPasswordChanged(it))
                },
                onSignUpButtonClick = {
                    when(item){
                        0 -> viewModel.onEvent(SignUpEvent.OnSignUpAsCustomerClick)
                        1 -> viewModel.onEvent(SignUpEvent.OnSignUpAsCompanyClick)
                    }
                }
            )
        }
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
            imageUrl = SIGNUP_SCREEN_CUSTOMER_IMAGE_URL,
            surfaceColor = SignUpCustomerBackgroundColor,
            targetOffsetValue = -constraints.maxHeight/4.toFloat() + 130f,
            isUiWindowOpen = state.isCustomerUiWindowOpen,
            onButtonClick = {
                viewModel.onEvent(SignUpEvent.OnCustomerWindowSignUpClick)
            }
        )
        AnimatableSignUpWindow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title =  I_AM_COMPANY_TEXT,
            details = COMPANY_STATEMENT_TEXT,
            buttonText = SIGNUP_UPPERCASE_TEXT,
            imageUrl = SIGNUP_SCREEN_COMPANY_IMAGE_URL,
            surfaceColor = SignUpCompanyBackgroundColor,
            targetOffsetValue = constraints.maxHeight/4.toFloat() - 130f,
            isUiWindowOpen = state.isCompanyUiWindowOpen,
            onButtonClick = {
                viewModel.onEvent(SignUpEvent.OnCompanyWindowSignUpClick)
            }
        )
    }
    BoxWithConstraints {
        SignUpScreenLogoAnimation(
            onSignUpScreenLogoClick = {
                viewModel.onEvent(SignUpEvent.OnLogoClick)
            }
        )
        CustomLogInButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = spacing.spaceMedium),
            text = LOGIN_TEXT,
            onClick = {
                viewModel.onEvent(SignUpEvent.OnLogInClick)
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

























