package com.commandiron.besonapp_clean_arch.presentation.signup_steps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.BACK_TO_SIGN_UP_SCREEN
import com.commandiron.besonapp_clean_arch.core.Strings.COMPLETE_REGISTRATION
import com.commandiron.besonapp_clean_arch.core.Strings.CREATE_PROFILE_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.ENTER_YOUR_PHONE_NUMBER
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.signup_steps.components.ClickableToGalleryImage
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun SignUpStepsScreen3(
    viewModel: SignUpStepsViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
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
            }
        }
    }
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.background
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
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        Text(
            text = CREATE_PROFILE_TEXT,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Text(
            text = ENTER_YOUR_PHONE_NUMBER,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        ClickableToGalleryImage(
            uri = state.profilePictureUri,
            onImageChange = { viewModel.onEvent(SignUpStepsUserEvent.PictureChanged(it)) }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            onClick = { viewModel.onEvent(SignUpStepsUserEvent.OnCompleteClick) }
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




















