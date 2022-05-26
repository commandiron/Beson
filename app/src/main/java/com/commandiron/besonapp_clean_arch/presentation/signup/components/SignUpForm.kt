package com.commandiron.besonapp_clean_arch.presentation.signup.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.commandiron.besonapp_clean_arch.core.Strings.EMAIL_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_AGAIN_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PASSWORD_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNUP_TEXT
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.presentation.signup.state.RegistrationFormState
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun SignUpForm(
    registrationFormState: RegistrationFormState,
    buttonText: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatedPasswordChanged: (String) -> Unit,
    onSignUpButtonClick:() -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = SIGNUP_TEXT,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        FormTextField(
            text = registrationFormState.email,
            hint = EMAIL_TEXT,
            textFieldErrorMessage = registrationFormState.emailError,
            keyboardType = KeyboardType.Email
        ){
            onEmailChanged(it)
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        FormTextField(
            text = registrationFormState.password,
            hint =  PASSWORD_TEXT,
            textFieldErrorMessage = registrationFormState.passwordError,
            keyboardType = KeyboardType.Password
        ){
            onPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        FormTextField(
            text = registrationFormState.repeatedPassword,
            hint =  PASSWORD_AGAIN_TEXT,
            textFieldErrorMessage = registrationFormState.repeatedPasswordError,
            keyboardType = KeyboardType.Password
        ){
            onRepeatedPasswordChanged(it)
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Button(onClick = { onSignUpButtonClick() }) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.h4
            )
        }
    }

}