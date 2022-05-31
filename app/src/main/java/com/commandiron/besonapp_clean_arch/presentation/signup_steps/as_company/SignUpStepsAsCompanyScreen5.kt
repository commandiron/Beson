package com.commandiron.besonapp_clean_arch.presentation.signup_steps.as_company

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.BACK_TO_SIGN_UP_SCREEN
import com.commandiron.besonapp_clean_arch.core.Strings.COMPLETE_REGISTRATION
import com.commandiron.besonapp_clean_arch.core.Strings.CREATE_PROFILE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SELECT_SUB_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsUiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsUserEvent
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.SignUpStepsViewModel
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.components.MultipleCategorySelector
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun SignUpStepsAsCompanyScreen5(
    viewModel: SignUpStepsViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    println("State in 5 " + state.selectedMainConstructionItem?.title)
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is SignUpStepsUiEvent.NavigateToSignUpScreen -> {
                    navController.navigate(
                        NavigationItem.SignUp.route
                    )
                }
                is SignUpStepsUiEvent.RegistrationSuccess -> {
                    navController.navigate(
                        NavigationItem.Profile.route
                    )
                }
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
        Text(
            modifier = Modifier
                .clickable { viewModel.onEvent(SignUpStepsUserEvent.OnBackToSignUpClick) }
                .align(Alignment.Start),
            text = BACK_TO_SIGN_UP_SCREEN,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
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
                viewModel.onEvent(SignUpStepsUserEvent.SubCategoriesSelected(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            onClick = { viewModel.onEvent(SignUpStepsUserEvent.SubConstructionCategoryScreenNext) }
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




















