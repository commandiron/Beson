package com.commandiron.besonapp_clean_arch.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.EMAIL_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.LOGIN_BESON_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.LOGIN_TEXT_2
import com.commandiron.besonapp_clean_arch.core.Strings.OR_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_TEXT
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.login.event.LogInUiEvent
import com.commandiron.besonapp_clean_arch.presentation.login.event.LogInUserEvent
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is LogInUiEvent.NavigateToSignUp -> {
                    navController.navigate(
                        NavigationItem.SignUp.route
                    )
                }
                is LogInUiEvent.LogInValidationAndFirebaseLogInSuccess -> {
                    navController.navigate(
                        NavigationItem.Profile.route
                    )
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
            text = LOGIN_BESON_TEXT,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        FormTextField(
            text = state.email,
            hint = EMAIL_TEXT,
            textFieldErrorMessage = state.emailError,
            onChange = {
                viewModel.onEvent(LogInUserEvent.EmailChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        FormTextField(
            text = state.password,
            hint = PASSWORD_TEXT,
            textFieldErrorMessage = state.passwordError,
            keyboardType = KeyboardType.Password,
            onChange = {
                viewModel.onEvent(LogInUserEvent.PasswordChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            modifier = Modifier.width(spacing.defaultButtonWidth),
            onClick = { viewModel.onEvent(LogInUserEvent.OnLogInClick) }
        ) {
            Text(
                text = LOGIN_TEXT_2,
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
            onClick = { viewModel.onEvent(LogInUserEvent.OnSignUpClick) }
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















