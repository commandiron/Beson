package com.commandiron.besonapp_clean_arch.presentation.signup.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.commandiron.besonapp_clean_arch.core.Strings.EMAIL_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_AGAIN_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_TEXT
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.presentation.signup.model.RegistrationFormState
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    placeholderModifier: Modifier = Modifier,
    registrationFormState: RegistrationFormState,
    buttonText: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatedPasswordChanged: (String) -> Unit,
    onSignUpButtonClick:() -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = placeholderModifier,
            text = SIGNUP_TEXT,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        FormTextField(
            modifier = placeholderModifier,
            text = registrationFormState.email,
            hint = EMAIL_TEXT,
            textFieldErrorMessage = registrationFormState.emailError,
            keyboardType = KeyboardType.Email
        ){
            onEmailChanged(it)
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        FormTextField(
            modifier = placeholderModifier,
            text = registrationFormState.password,
            hint =  PASSWORD_TEXT,
            textFieldErrorMessage = registrationFormState.passwordError,
            keyboardType = KeyboardType.Password
        ){
            onPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        FormTextField(
            modifier = placeholderModifier,
            text = registrationFormState.repeatedPassword,
            hint =  PASSWORD_AGAIN_TEXT,
            textFieldErrorMessage = registrationFormState.repeatedPasswordError,
            keyboardType = KeyboardType.Password
        ){
            onRepeatedPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Button(
            modifier = placeholderModifier,
            onClick = { onSignUpButtonClick() }
        ) {
            Text(
                text = buttonText,
            )
        }
    }

}