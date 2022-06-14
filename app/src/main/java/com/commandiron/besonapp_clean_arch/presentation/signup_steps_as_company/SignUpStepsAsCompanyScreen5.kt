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
import com.commandiron.besonapp_clean_arch.core.Strings.COMPLETE_REGISTRATION
import com.commandiron.besonapp_clean_arch.core.Strings.CREATE_PROFILE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SELECT_SUB_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company.components.MultipleCategorySelector
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun SignUpStepsAsCompanyScreen5(
    viewModel: SignUpStepsAsCompanyViewModel = hiltViewModel(),
    navigateTo: (NavigationOptions) -> Unit,
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
                is UiEvent.NavigateTo -> navigateTo(event.navigationOptions)
                is UiEvent.ShowLoadingScreen -> showLoadingScreen(event.message)
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                is UiEvent.HideLoadingScreen -> hideLoadingScreen()
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
            text = SELECT_SUB_CONSTRUCTION_CATEGORY,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        MultipleCategorySelector(
            itemList = state.selectedMainConstructionItem?.subConstructionItems ?: listOf(),
            selectedSubConstructionItems = state.selectedSubConstructionItems,
            onItemSelected = {
                viewModel.onEvent(SignUpStepsAsCompanyUserEvent.SubCategoriesSelected(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            onClick = { viewModel.onEvent(SignUpStepsAsCompanyUserEvent.SubConstructionCategoryScreenNext) }
        ) {
            Text(text = COMPLETE_REGISTRATION)
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




















