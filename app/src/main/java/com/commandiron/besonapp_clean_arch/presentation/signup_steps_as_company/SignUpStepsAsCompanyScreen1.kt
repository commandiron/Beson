package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.CREATE_PROFILE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.ENTER_YOUR_NAME_OR_COMPANY_NAME
import com.commandiron.besonapp_clean_arch.core.Strings.NEXT
import com.commandiron.besonapp_clean_arch.core.Strings.YOUR_NAME
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.components.RegistrationTextField
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun SignUpStepsAsCompanyScreen1(
    viewModel: SignUpStepsAsCompanyViewModel = hiltViewModel(),
    navigateUp:() -> Unit,
    navigateTo: (NavigationOptions) -> Unit
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.NavigateUp -> navigateUp()
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
            .statusBarsPadding()
            .padding(horizontal = spacing.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        Text(
            text = CREATE_PROFILE_TEXT,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Text(
            text = ENTER_YOUR_NAME_OR_COMPANY_NAME,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        RegistrationTextField(
            text = state.name ?: "",
            hint = YOUR_NAME,
            onChange = { viewModel.onEvent(SignUpStepsAsCompanyUserEvent.NameChanged(it)) }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(onClick = { viewModel.onEvent(SignUpStepsAsCompanyUserEvent.NameScreenNext) }) {
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




















