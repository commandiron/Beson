package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.MY_FAVORITE_PROFILES
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.core.Strings.PROFILE_WILL_BE_REMOVED_FROM_FAVORITES_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.Strings.TELEPHONE_NUMBER_WITH_TURKEY_PHONE_CODE
import com.commandiron.besonapp_clean_arch.core.Strings.YOUR_PRICE_WILL_GONE_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.CustomAlertDialog
import com.commandiron.besonapp_clean_arch.presentation.components.DoneDialog
import com.commandiron.besonapp_clean_arch.presentation.components.ProfileImage
import com.commandiron.besonapp_clean_arch.presentation.profile.components.CustomExpandableMenu
import com.commandiron.besonapp_clean_arch.presentation.profile.components.MyUpdatesExpandedMenuWithCarousel
import com.commandiron.besonapp_clean_arch.presentation.profile.components.FavoriteProfilesExpandedMenuWithCarousel
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import com.commandiron.besonapp_clean_arch.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateTo:(NavigationOptions) -> Unit,
    showLoadingScreen:(String) -> Unit,
    hideLoadingScreen:() -> Unit,
    showSnackbar:(String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    val fabState = LocalFabState.current
    LaunchedEffect(key1 = state.userType){
        when(state.userType){
            UserType.CUSTOMER -> fabState.targetState = false
            UserType.COMPANY -> fabState.targetState = true
        }
    }
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                UiEvent.HideKeyboard -> hideKeyboard()
                is UiEvent.NavigateTo -> navigateTo(event.navigationOptions)
                is UiEvent.ShowLoadingScreen -> showLoadingScreen(event.message)
                is UiEvent.HideLoadingScreen -> hideLoadingScreen()
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.spaceLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(state.imageUrl)
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Column {
                    Text(
                        text = state.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = TELEPHONE_NUMBER_WITH_TURKEY_PHONE_CODE + state.phoneNumber,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
            IconButton(
                modifier = Modifier.size(spacing.spaceMedium),
                onClick = { viewModel.onEvent(ProfileUserEvent.OnEditClick) }
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Divider(Modifier.padding(horizontal = spacing.spaceMedium))
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        if(state.userType == UserType.COMPANY){
            CustomExpandableMenu(
                title = MY_PRICE_UPDATES,
                isExpanded = state.myUpdatesSurfaceExpanded,
                onDropDownIconClick = { viewModel.onEvent(ProfileUserEvent.MyUpdatesDropDownIconClick) }
            ){
                state.myPrices?.let {
                    MyUpdatesExpandedMenuWithCarousel(
                        height = spacing.expandableMenuHeight,
                        myPrices = it,
                        onDelete = { priceItem ->
                            viewModel.onEvent(ProfileUserEvent.DeleteMyUpdate(priceItem))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }

        CustomExpandableMenu(
            title = MY_FAVORITE_PROFILES,
            isExpanded = state.myFavoriteProfilesSurfaceExpanded,
            onDropDownIconClick = { viewModel.onEvent(ProfileUserEvent.FavoriteProfilesDropDownIconClick) }
        ){
            state.favoriteUserProfiles?.let {
                FavoriteProfilesExpandedMenuWithCarousel(
                    height = spacing.expandableMenuHeight,
                    userProfiles = it,
                    unFavorite = { userUid ->
                        viewModel.onEvent(ProfileUserEvent.UnFavoriteProfile(userUid))
                    }
                )
            }
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