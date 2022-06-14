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
import com.commandiron.besonapp_clean_arch.AppViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.CANCEL
import com.commandiron.besonapp_clean_arch.core.Strings.LOG_OUT
import com.commandiron.besonapp_clean_arch.core.Strings.MAIN_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.NAME
import com.commandiron.besonapp_clean_arch.core.Strings.PHONE_NUMBER
import com.commandiron.besonapp_clean_arch.core.Strings.SAVE
import com.commandiron.besonapp_clean_arch.core.Strings.SUB_CONSTRUCTION_CATEGORIES
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.components.CategoryItem
import com.commandiron.besonapp_clean_arch.presentation.components.FormTextField
import com.commandiron.besonapp_clean_arch.presentation.components.ClickableToGalleryImage
import com.commandiron.besonapp_clean_arch.ui.theme.*

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateTo:(NavigationOptions) -> Unit,
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
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        state.selectedMainConstructionItem?.let {
            Text(
                text = MAIN_CONSTRUCTION_CATEGORY,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Divider()
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            CategoryItem(
                modifier = Modifier
                    .heightIn(max = spacing.spaceExtraLarge)
                    .aspectRatio(1f)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground),
                itemMain = it,
                iconPadding = spacing.spaceExtraSmall
            ){}
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
        }
        state.selectedSubConstructionItems?.let {
            Text(
                text = SUB_CONSTRUCTION_CATEGORIES,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Divider()
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            LazyRow {
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
        Text(
            modifier = Modifier.clickable { viewModel.onEvent(EditProfileUserEvent.LogOut) },
            text = LOG_OUT,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal
            ),
            color = ErrorRed
        )
    }
}




















