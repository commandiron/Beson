package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer

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
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.CREATE_PROFILE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.ENTER_YOUR_PHONE_NUMBER
import com.commandiron.besonapp_clean_arch.core.Strings.NEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PHONE_NUMBER_MASK
import com.commandiron.besonapp_clean_arch.core.Strings.YOUR_PHONE_NUMBER
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.components.RegistrationTextField
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun SignUpStepsAsCustomerScreen2(
    viewModel: SignUpStepsAsCustomerViewModel = hiltViewModel(),
    navigateTo: (NavigationOptions) -> Unit
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.NavigateTo -> navigateTo(event.navigationOptions)
                else -> {}
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.background
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceXXLarge))
        Text(
            text = CREATE_PROFILE_TEXT,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Text(
            text = ENTER_YOUR_PHONE_NUMBER,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        RegistrationTextField(
            text = state.phoneNumber ?: "",
            hint = PHONE_NUMBER_MASK,
            keyboardType = KeyboardType.Phone,
            onChange = {
                viewModel.onEvent(SignUpStepsAsCustomerUserEvent.PhoneNumberChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            onClick = {
                viewModel.onEvent(SignUpStepsAsCustomerUserEvent.PhoneNumberScreenNext)
            }
        ) {
            Text(text = NEXT)
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




















