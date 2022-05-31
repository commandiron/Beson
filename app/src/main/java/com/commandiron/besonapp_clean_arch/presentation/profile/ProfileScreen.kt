package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.presentation.profile.components.DraggableProfileHeader
import com.commandiron.besonapp_clean_arch.presentation.profile.components.ProfileItem
import com.commandiron.besonapp_clean_arch.presentation.profile.event.ProfileUiEvent
import com.commandiron.besonapp_clean_arch.presentation.profile.event.ProfileUserEvent
import com.commandiron.besonapp_clean_arch.ui.theme.LocalCoroutineScope
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val navController = LocalNavController.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is ProfileUiEvent.NavigateToEditProfile -> {
                    navController.navigate(
                        NavigationItem.EditProfile.route
                    )
                }
                is ProfileUiEvent.NavigateToMyPriceUpdates -> {
                    navController.navigate(
                        NavigationItem.MyPriceUpdates.route
                    )
                }
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
        text = state.name,
        imageUrl = state.imageUrl,
        onEditButtonClick = {
            viewModel.onEvent(ProfileUserEvent.OnEditClick)
        },
        onVerticalDrag = {
            viewModel.onEvent(ProfileUserEvent.OnProfileHeaderVerticalDrag(it))
        }
    )
    Column(
        modifier = Modifier.offset(y = state.profileHeaderHeight)
    ) {
        Divider(
            modifier = Modifier
                .padding(horizontal = spacing.spaceMedium),
            thickness = 1.dp,
        )
        ProfileItem(
            title = MY_PRICE_UPDATES,
            onItemClick = { viewModel.onEvent(ProfileUserEvent.OnMyUpdatesClick) }
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = spacing.spaceMedium),
            thickness = 1.dp,
        )
    }
}