package com.commandiron.besonapp_clean_arch.presentation.customer_or_company

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.COMPANY_STATEMENT_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.CONTINUE_AS_COMPANY
import com.commandiron.besonapp_clean_arch.core.Strings.CONTINUE_AS_CUSTOMER
import com.commandiron.besonapp_clean_arch.core.Strings.CUSTOMER_STATEMENT_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.CustomAlertDialog
import com.commandiron.besonapp_clean_arch.presentation.signup.components.AnimatableSignUpWindow
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCompanyBackgroundColor
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCustomerBackgroundColor

@Composable
fun CustomerOrCompanyScreen(
    viewModel: CustomerOrCompanyViewModel = hiltViewModel(),
    navigateTo:(NavigationOptions) -> Unit,
    showSnackbar:(String) -> Unit,
) {
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.NavigateTo -> navigateTo(event.navigationOptions)
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                else -> {}
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.tertiary
    )
    Column() {
        AnimatableSignUpWindow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title =  I_AM_CUSTOMER_TEXT,
            details = CUSTOMER_STATEMENT_TEXT,
            buttonText = CONTINUE_AS_CUSTOMER,
            backgroundImageUrl = state.customerWindowsBackgroundUrl,
            surfaceColor = SignUpCustomerBackgroundColor,
            onButtonClick = { viewModel.onEvent(CustomerOrCompanyUserEvent.CustomerClick) }
        )
        AnimatableSignUpWindow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title =  I_AM_COMPANY_TEXT,
            details = COMPANY_STATEMENT_TEXT,
            buttonText = CONTINUE_AS_COMPANY,
            backgroundImageUrl = state.companyWindowsBackgroundUrl,
            surfaceColor = SignUpCompanyBackgroundColor,
            onButtonClick = { viewModel.onEvent(CustomerOrCompanyUserEvent.CompanyClick) }
        )
    }
    if(state.showAlertDialog){
        CustomAlertDialog(
            title = state.alertDialogTitle,
            onDismissRequest = { viewModel.onEvent(CustomerOrCompanyUserEvent.AlertDialogDismiss) },
            onConfirm = { viewModel.onEvent(CustomerOrCompanyUserEvent.AlertDialogConfirm) },
            onDismiss = { viewModel.onEvent(CustomerOrCompanyUserEvent.AlertDialogDismiss) }
        )
    }
}