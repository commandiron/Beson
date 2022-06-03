package com.commandiron.besonapp_clean_arch.presentation.edit_profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.CANCEL
import com.commandiron.besonapp_clean_arch.core.Strings.LOG_OUT
import com.commandiron.besonapp_clean_arch.core.Strings.MAIN_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.NAME
import com.commandiron.besonapp_clean_arch.core.Strings.PHONE_NUMBER
import com.commandiron.besonapp_clean_arch.core.Strings.SAVE
import com.commandiron.besonapp_clean_arch.core.Strings.SNACKBAR_HIDE_ACTION_TEXT
import com.commandiron.besonapp_clean_arch.core.Strings.SUB_CONSTRUCTION_CATEGORIES
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.components.CategoryItem
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.presentation.components.ClickableToGalleryImage
import com.commandiron.besonapp_clean_arch.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = LocalCoroutineScope.current
    val navController = LocalNavController.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message, SNACKBAR_HIDE_ACTION_TEXT)
                    }
                }
                else -> {}
            }
        }
    }
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.tertiary
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
                text = CANCEL,
                modifier = Modifier.clickable {
                    viewModel.onEvent(EditProfileUserEvent.Cancel)
                }
            )
            ClickableToGalleryImage(
                imageUrl = state.imageUrl,
                onImageChange = {
                    viewModel.onEvent(EditProfileUserEvent.PictureChanged(it))
                }
            )
            Text(
                text = SAVE,
                modifier = Modifier.clickable {
                    viewModel.onEvent(EditProfileUserEvent.Save)
                }
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        FormTextField(
            text = state.name,
            hint = NAME,
            onChange = {
                viewModel.onEvent(EditProfileUserEvent.NameChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        FormTextField(
            text = state.phoneNumber,
            hint = PHONE_NUMBER,
            keyboardType = KeyboardType.Phone,
            onChange = {
                viewModel.onEvent(EditProfileUserEvent.PhoneNumberChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = MAIN_CONSTRUCTION_CATEGORY,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Divider()
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        state.selectedMainConstructionItem?.let {
            CategoryItem(
                modifier = Modifier
                    .heightIn(max = spacing.spaceExtraLarge)
                    .aspectRatio(1f)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground),
                itemMain = it,
                iconPadding = spacing.spaceExtraSmall
            ){}
        }
        Text(
            text = SUB_CONSTRUCTION_CATEGORIES,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Divider()
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        state.selectedSubConstructionItems?.let {
            LazyRow() {
                items(it){ item ->
                    CategoryItem(
                        modifier = Modifier
                            .heightIn(max = spacing.spaceExtraLarge)
                            .aspectRatio(1f)
                            .border(1.dp, MaterialTheme.colorScheme.onBackground),
                        itemMain = item,
                        iconPadding = spacing.spaceExtraSmall
                    ){}
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f).padding(horizontal = spacing.spaceMedium),
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.onEvent(EditProfileUserEvent.LogOut) }
            ) {
                Text(
                    text = LOG_OUT,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
                )
            }
            Divider(
                modifier = Modifier.weight(1f).padding(horizontal = spacing.spaceMedium),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}




















