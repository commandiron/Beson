package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.MY_FAVORITE_PROFILES
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.core.Strings.PROFILE_WILL_BE_REMOVED_FROM_FAVORITES_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.Strings.SNACKBAR_HIDE_ACTION_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.YOUR_PRICE_WILL_GONE_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.CustomAlertDialog
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.LinearProgressLoadingDialog
import com.commandiron.besonapp_clean_arch.presentation.components.DoneDialog
import com.commandiron.besonapp_clean_arch.presentation.profile.components.CustomExpandableMenu
import com.commandiron.besonapp_clean_arch.presentation.profile.components.DraggableProfileHeader
import com.commandiron.besonapp_clean_arch.presentation.profile.components.MyUpdatesExpandedMenuWithCarousel
import com.commandiron.besonapp_clean_arch.presentation.profile.components.FavoriteProfilesExpandedMenuWithCarousel
import com.commandiron.besonapp_clean_arch.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateTo:(String) -> Unit,
    showHideLoadingScreen:(String) -> Unit,
    showSnackbar:(String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                UiEvent.HideKeyboard -> hideKeyboard()
                is UiEvent.NavigateTo -> navigateTo(event.route)
                is UiEvent.ShowHideLoadingScreen -> showHideLoadingScreen(event.message)
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                else -> {}
            }
        }
    }
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    DraggableProfileHeader(
        title = state.name,
        imageUrl = state.imageUrl,
        onEditButtonClick = {
            viewModel.onEvent(ProfileUserEvent.OnEditClick)
        },
        onVerticalDrag = {
            viewModel.onEvent(ProfileUserEvent.OnProfileHeaderVerticalDrag(it))
        }
    )
    Column(
        modifier = Modifier.offset(y = state.profileHeaderHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(Modifier.padding(horizontal = spacing.spaceMedium))
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        CustomExpandableMenu(
            title = MY_PRICE_UPDATES,
            isExpanded = state.myUpdatesSurfaceExpanded,
            onDropDownIconClick = { viewModel.onEvent(ProfileUserEvent.MyUpdatesDropDownIconClick) }
        ){
            MyUpdatesExpandedMenuWithCarousel(
                height = spacing.expandableMenuHeight,
                myUpdates = state.myUpdates,
                onDelete = { viewModel.onEvent(ProfileUserEvent.DeleteMyUpdate(it)) }
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        CustomExpandableMenu(
            title = MY_FAVORITE_PROFILES,
            isExpanded = state.myFavoriteProfilesSurfaceExpanded,
            onDropDownIconClick = { viewModel.onEvent(ProfileUserEvent.FavoriteProfilesDropDownIconClick) }
        ){
            FavoriteProfilesExpandedMenuWithCarousel(
                height = spacing.expandableMenuHeight,
                userProfiles = state.favoriteUserProfiles,
                unFavorite = {
                    viewModel.onEvent(ProfileUserEvent.UnFavoriteProfile(it))
                }
            )
        }
    }
    if(state.showDeleteMyUpdateAlertDialog){
        CustomAlertDialog(
            title = YOUR_PRICE_WILL_GONE_ARE_YOU_SURE,
            onDismissRequest = { viewModel.onEvent(ProfileUserEvent.DeleteMyUpdateAlertDialogDismiss) },
            onConfirm = { viewModel.onEvent(ProfileUserEvent.DeleteMyUpdateAlertDialogConfirm) },
            onDismiss = { viewModel.onEvent(ProfileUserEvent.DeleteMyUpdateAlertDialogDismiss) }
        )
    }
    if(state.isLoading){
        LinearProgressLoadingDialog(title = state.loadingMessage)
    }
    if(state.showUnFavoriteAlertDialog){
        CustomAlertDialog(
            title = PROFILE_WILL_BE_REMOVED_FROM_FAVORITES_ARE_YOU_SURE,
            onDismissRequest = { viewModel.onEvent(ProfileUserEvent.UnFavoriteAlertDialogDismiss) },
            onConfirm = { viewModel.onEvent(ProfileUserEvent.UnFavoriteAlertDialogConfirm) },
            onDismiss = { viewModel.onEvent(ProfileUserEvent.UnFavoriteAlertDialogDismiss) }
        )
    }
    if(state.showDoneDialog){
        DoneDialog(title = state.doneDialogMessage){
            viewModel.onEvent(ProfileUserEvent.DoneDialogDismiss)
        }
    }
}