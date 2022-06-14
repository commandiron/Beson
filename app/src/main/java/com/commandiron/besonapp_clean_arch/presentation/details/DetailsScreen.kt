package com.commandiron.besonapp_clean_arch.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.ADD_TO_FAVORITES
import com.commandiron.besonapp_clean_arch.core.Strings.REMOVE_FROM_FAVORITES
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.components.LogoWithAppName
import com.commandiron.besonapp_clean_arch.presentation.components.ProfileImage
import com.commandiron.besonapp_clean_arch.presentation.model.FavoriteStatus
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(spacing.spaceExtraLarge),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = spacing.spaceLarge),
            contentAlignment = Alignment.TopStart
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = null,
                modifier = Modifier
                    .size(spacing.spaceLarge)
                    .clickable {
                        viewModel.onEvent(DetailsUserEvent.Back)
                    },
                tint = MaterialTheme.colorScheme.background,
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
        ProfileImage(
            imageUrl = state.imageUrl,
            size = 100.dp
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = state.name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = state.phoneNumber,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        if(state.favoriteStatus != FavoriteStatus.MY_PROFILE){
            Text(
                text = if(state.favoriteStatus == FavoriteStatus.ALREADY_IN_FAVORITES) {
                    REMOVE_FROM_FAVORITES
                } else ADD_TO_FAVORITES,
                style = MaterialTheme.typography.bodyMedium,
                color = if(state.favoriteStatus == FavoriteStatus.ALREADY_IN_FAVORITES) {
                    ErrorRed
                }else NoErrorGreen,
                modifier = Modifier.clickable {
                    viewModel.onEvent(DetailsUserEvent.Favorite)
                }
            )
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




















