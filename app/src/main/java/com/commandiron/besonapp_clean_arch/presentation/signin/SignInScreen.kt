package com.commandiron.besonapp_clean_arch.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.EMAIL_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_BESON_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_TEXT_2
import com.commandiron.besonapp_clean_arch.core.Strings.OR_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SNACKBAR_HIDE_ACTION_TEXT
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.ui.theme.LocalCoroutineScope
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSnackbarHostState
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = LocalCoroutineScope.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val navController = LocalNavController.current
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
        Button(
            modifier = Modifier.width(spacing.defaultButtonWidth),
            onClick = { viewModel.onEvent(SignInUserEvent.OnSignInClick) }
        ) {
            Text(
                text = SIGN_IN_TEXT_2,
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Text(
            text = OR_TEXT,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            modifier = Modifier.width(spacing.defaultButtonWidth),
            onClick = { viewModel.onEvent(SignInUserEvent.OnSignUpClick) }
        ) {
            Text(
                text = SIGNUP_TEXT,
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceExtraLarge),
        contentAlignment = Alignment.BottomCenter
    ) {
        LogoWithAppName()
    }
}















