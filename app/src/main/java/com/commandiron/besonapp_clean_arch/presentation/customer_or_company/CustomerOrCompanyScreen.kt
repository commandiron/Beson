package com.commandiron.besonapp_clean_arch.presentation.customer_or_company

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_COMPANY_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.I_AM_CUSTOMER_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.PLEASE_MAKE_YOUR_CHOICE
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_WILL_BE_SENT_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.post_price.PostPriceUserEvent
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.CustomAlertDialog
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun CustomerOrCompanyScreen(
    viewModel: CustomerOrCompanyViewModel = hiltViewModel(),
    navigateTo:(String) -> Unit,
    showSnackbar:(String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.NavigateTo -> navigateTo(event.route)
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
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
            .padding(horizontal = spacing.spaceMedium, vertical = spacing.spaceExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = PLEASE_MAKE_YOUR_CHOICE,
            style = MaterialTheme.typography.bodyMedium
                .copy(fontWeight = FontWeight.Normal)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.75f),
                onClick = { viewModel.onEvent(CustomerOrCompanyUserEvent.CustomerClick) }
            ) {
                Text(
                    text = I_AM_CUSTOMER_TEXT,
                    style = MaterialTheme.typography.titleMedium
                        .copy(fontWeight = FontWeight.Normal)
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            Divider(color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            Button(
                modifier = Modifier.fillMaxWidth(0.75f),
                onClick = { viewModel.onEvent(CustomerOrCompanyUserEvent.CompanyClick) }
            ) {
                Text(
                    text = I_AM_COMPANY_TEXT,
                    style = MaterialTheme.typography.titleMedium
                        .copy(fontWeight = FontWeight.Normal)
                )
            }
        }
        LogoWithAppName()
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