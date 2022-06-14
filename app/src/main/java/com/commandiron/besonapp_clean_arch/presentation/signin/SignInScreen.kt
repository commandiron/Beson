package com.commandiron.besonapp_clean_arch.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.EMAIL_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_BESON_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_TEXT_2
import com.commandiron.besonapp_clean_arch.core.Strings.OR_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNING_IN
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_WITH_GOOGLE
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.components.GoogleSignInButton
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.presentation.components.GoogleSignInLauncher
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateTo:(NavigationOptions) -> Unit,
    showLoadingScreen:(String) -> Unit,
    hideLoadingScreen:() -> Unit,
    showSnackbar:(String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                UiEvent.HideKeyboard -> hideKeyboard()
                is UiEvent.NavigateTo -> navigateTo(event.navigationOptions)
                is UiEvent.ShowLoadingScreen -> showLoadingScreen(event.message)
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                UiEvent.HideLoadingScreen -> hideLoadingScreen()
                else -> {}
            }
        }
    }
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = spacing.spaceXXLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = SIGN_IN_BESON_TEXT,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        FormTextField(
            text = state.email,
            hint = EMAIL_TEXT,
            textFieldErrorMessage = state.emailError,
            onChange = {
                viewModel.onEvent(SignInUserEvent.EmailChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        FormTextField(
            text = state.password,
            hint = PASSWORD_TEXT,
            textFieldErrorMessage = state.passwordError,
            keyboardType = KeyboardType.Password,
            onChange = {
                viewModel.onEvent(SignInUserEvent.PasswordChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Row() {
            Button(
                modifier = Modifier.width(spacing.defaultButtonWidth),
                onClick = { viewModel.onEvent(SignInUserEvent.OnSignUpClick) }
            ) {
                Text(
                    text = SIGNUP_TEXT,
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Button(
                modifier = Modifier.width(spacing.defaultButtonWidth),
                onClick = { viewModel.onEvent(SignInUserEvent.OnSignInClick) }
            ) {
                Text(
                    text = SIGN_IN_TEXT_2,
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Text(
            text = OR_TEXT,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        GoogleSignInButton(
            text = SIGN_IN_WITH_GOOGLE,
            loadingText = SIGNING_IN,
            isLoading = state.isGoogleLoading,
            onClick = {
                viewModel.onEvent(SignInUserEvent.GoogleSignInButtonClick)
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceExtraLarge),
        contentAlignment = Alignment.BottomCenter
    ) {
        LogoWithAppName()
    }
    GoogleSignInLauncher(
        signInRequestCode = 1,
        enabled = state.launchGoogleSignIn,
        onFailed = { SignInUserEvent.GoogleSignInFailed(SORRY_SOMETHING_BAD_HAPPENED) },
        onSuccessful = {
            viewModel.onEvent(
                SignInUserEvent.GoogleSignInSuccessful(it.idToken)
            )
        }
    )
}

















