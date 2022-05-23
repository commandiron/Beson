package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatableSignUpWindow
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatedAppExplainingStrip
import com.commandiron.besonapp_clean_arch.presentation.signup.components.CustomLogInButton
import com.commandiron.besonapp_clean_arch.presentation.signup.components.SignUpForm
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpEvent
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCompanyBackgroundColor
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCustomerBackgroundColor
import com.example.besonapp.presentation.SignUpScreenLogoAnimation
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun SignUpScreen(
    systemUiController: SystemUiController,
    onCustomerSignUpClick:() -> Unit,
    onCompanySignUpClick:() -> Unit,
    onLogInClick: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.Success -> {
                    when(state.userType){
                        UserType.CUSTOMER -> {onCustomerSignUpClick()}
                        UserType.COMPANY -> {onCompanySignUpClick()}
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
        val constraints = this.constraints
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ){
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                SignUpForm(
                    registrationFormState = state.registrationFormState,
                    buttonText = SIGN_UP_AS_CUSTOMER_TEXT,
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
                        viewModel.onEvent(SignUpEvent.OnSignUpCustomerClick)
                    }
                )
                AnimatableSignUpWindow(
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
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                SignUpForm(
                    registrationFormState = state.registrationFormState,
                    buttonText = SIGN_UP_AS_COMPANY_TEXT,
                    onEmailChanged = {
                        viewModel.onEvent(SignUpEvent.EmailChanged(it))
                    },
                    onPasswordChanged = {
                        viewModel.onEvent(SignUpEvent.PasswordChanged(it))
                    },
                    onRepeatedPasswordChanged = {
                        viewModel.onEvent(
                            SignUpEvent.RepeatedPasswordChanged(repeatedPassword = it)
                        )
                    },
                    onSignUpButtonClick = {
                        viewModel.onEvent(SignUpEvent.OnSignUpCompanyClick)
                    }
                )
                AnimatableSignUpWindow(
                    modifier = Modifier.fillMaxSize(),
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
        }
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
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.spaceLarge)
            ,
            isAnimated = true
        )
    }
}

























