package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Constraints
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.AppViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.COMPANY_STATEMENT_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.CUSTOMER_STATEMENT_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.EMAIL_SIGN_IN
import com.commandiron.besonapp_clean_arch.core.Strings.GOOGLE_SIGN_IN
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNING_IN
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_UPPERCASE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_AS_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatableSignUpWindow
import com.commandiron.besonapp_clean_arch.presentation.components.AnimatedAppExplainingStrip
import com.commandiron.besonapp_clean_arch.presentation.components.GoogleSignInButton
import com.commandiron.besonapp_clean_arch.presentation.components.GoogleSignInLauncher
import com.commandiron.besonapp_clean_arch.presentation.signup.components.CustomLogInButton
import com.commandiron.besonapp_clean_arch.presentation.signup.components.SignUpForm
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.example.besonapp.presentation.SignUpScreenLogoAnimation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateTo:(String) -> Unit,
    showLoadingScreen:(String) -> Unit,
    hideLoadingScreen:() -> Unit,
    showSnackbar:(String) -> Unit,
) {
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                UiEvent.HideKeyboard -> hideKeyboard()
                is UiEvent.NavigateTo -> navigateTo(event.route)
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                is UiEvent.ShowLoadingScreen -> showLoadingScreen(event.message)
                UiEvent.HideLoadingScreen -> hideLoadingScreen()
                else -> {}
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.tertiary
    )
    val placeholderModifier = Modifier.placeholder(
        visible = state.enablePlaceHolder,
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
                viewModel.onEvent(SignUpUserEvent.SignUpUserAsCustomerClick)
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
                viewModel.onEvent(SignUpUserEvent.SignUpUserAsCompanyClick)
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
                viewModel.onEvent(SignUpUserEvent.CustomerWindowSignUpButtonClick)
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
                viewModel.onEvent(SignUpUserEvent.CompanyWindowSignUpButtonClick)
            }
        )
    }
    Box{
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GoogleSignInButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.spaceMedium),
                text = GOOGLE_SIGN_IN,
                loadingText = SIGNING_IN,
                isLoading = state.isGoogleLoading,
                textColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                borderColor = MaterialTheme.colorScheme.tertiary,
                onClick = { viewModel.onEvent(SignUpUserEvent.GoogleSignInButtonClick) }
            )
            Spacer(modifier = Modifier.width(spacing.spaceXXLarge))
            CustomLogInButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = spacing.spaceMedium),
                placeholderModifier = placeholderModifier,
                text = EMAIL_SIGN_IN,
                onClick = {
                    viewModel.onEvent(SignUpUserEvent.SignInClick)
                }
            )
        }
        SignUpScreenLogoAnimation(
            modifier = Modifier.align(Alignment.Center),
            placeholderModifier = placeholderModifier,
            onSignUpScreenLogoClick = {
                viewModel.onEvent(SignUpUserEvent.LogoClick)
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
    GoogleSignInLauncher(
        signInRequestCode = 1,
        enabled = state.launchGoogleSignIn,
        onFailed = { viewModel.onEvent(SignUpUserEvent.GoogleSignInFailed(SORRY_SOMETHING_BAD_HAPPENED)) },
        onSuccessful = { viewModel.onEvent(SignUpUserEvent.GoogleSignInSuccessful(it.idToken)) }
    )
}

























