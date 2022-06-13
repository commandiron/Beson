package com.commandiron.besonapp_clean_arch.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.BACK
import com.commandiron.besonapp_clean_arch.core.Strings.NAME
import com.commandiron.besonapp_clean_arch.core.Strings.PHONE_NUMBER
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.components.ProfileImage
import com.commandiron.besonapp_clean_arch.ui.theme.*

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateUp: () -> Unit,
    navigateTo: (String) -> Unit,
    showLoadingScreen: (String) -> Unit,
    hideLoadingScreen: () -> Unit,
    showSnackbar: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                UiEvent.HideKeyboard -> hideKeyboard()
                is UiEvent.NavigateTo -> navigateTo(event.route)
                is UiEvent.ShowLoadingScreen -> showLoadingScreen(event.message)
                is UiEvent.HideLoadingScreen -> hideLoadingScreen()
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                UiEvent.NavigateUp -> navigateUp()
            }
        }
    }
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMediumLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = BACK,
                modifier = Modifier.clickable {
                    viewModel.onEvent(DetailsUserEvent.Back)
                }
            )
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = null,
                modifier = Modifier.clickable {
                    viewModel.onEvent(DetailsUserEvent.Favorite)
                },
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ProfileImage(
            imageUrl = state.imageUrl,
            size = 100.dp
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(text = state.name)
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(text = state.phoneNumber)
    }
}




















