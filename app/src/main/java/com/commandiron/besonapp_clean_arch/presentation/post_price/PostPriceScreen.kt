package com.commandiron.besonapp_clean_arch.presentation.post_price

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocationDisabled
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.CHOOSE
import com.commandiron.besonapp_clean_arch.core.Strings.CURRENCY_SYMBOL
import com.commandiron.besonapp_clean_arch.core.Strings.ENTER_PRICE
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_POSTED
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_WILL_BE_LISTED_AS_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.Strings.SELECT_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.SELECT_PRICE_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.SEND
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.components.DoneDialog
import com.commandiron.besonapp_clean_arch.presentation.components.MyGoogleMap
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.*
import com.commandiron.besonapp_clean_arch.ui.theme.*
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun PostPriceScreen(
    viewModel: PostPriceViewModel = hiltViewModel(),
    hideKeyboard:() -> Unit,
    navigateTo:(NavigationOptions) -> Unit,
    showLoadingScreen:(String) -> Unit,
    hideLoadingScreen:() -> Unit,
    showSnackbar:(String) -> Unit
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val permissionsState = LocalPermissionsState.current
    LaunchedEffect(key1 = permissionsState.revokedPermissions) {
        permissionsState.permissions.forEach { perm ->
            when(perm.permission){
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when(perm.status){
                        PermissionStatus.Granted -> {
                            viewModel.onEvent(PostPriceUserEvent.FineLocationPermissionGranted)
                        }
                        is PermissionStatus.Denied -> {
                            if(perm.status.shouldShowRationale){
                                viewModel.onEvent(PostPriceUserEvent.FineLocationPermissionDenied)
                            }else{
                                //Waiting for response
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = true){
        permissionsState.launchMultiplePermissionRequest()
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
    val placeholderModifier = Modifier.placeholder(
        visible = state.placeholderIsVisible,
        highlight = if(state.priceIsSent) null else PlaceholderHighlight.shimmer()
    )
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(spacing.spaceLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = placeholderModifier,
                        text = SELECT_CATEGORY,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    CustomDropDown(
                        modifier = placeholderModifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp),
                        borderColor = state.subConsCategoryBorderColor,
                        onCategoryBoxClick = { viewModel.onEvent(PostPriceUserEvent.SubConsCategorySelectionBoxClick) },
                        title = state.selectedSubConsItem?.title ?: CHOOSE,
                        isExpanded = state.subConsCategoryDropDownMenuIsExpanded,
                        offset = DpOffset(x= spacing.spaceMedium, y= spacing.spaceExtraSmall),
                        dropDownItems = state.subConsItems?.map { it.title },
                        onSelect = { viewModel.onEvent(PostPriceUserEvent.OnSubConstructionCategorySelect(it)) },
                        onDismissRequest = { viewModel.onEvent(PostPriceUserEvent.SubConsCategoryDismiss) }
                    )
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = placeholderModifier,
                        text = SELECT_PRICE_CATEGORY,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    CustomDropDown(
                        modifier = placeholderModifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp),
                        borderColor = state.priceCategoryBorderColor,
                        onCategoryBoxClick = { viewModel.onEvent(PostPriceUserEvent.PriceCategorySelectionBoxClick) },
                        title = state.selectedPriceTitle ?: CHOOSE,
                        isExpanded = state.priceCategoryDropDownMenuIsExpanded,
                        offset = DpOffset(x= spacing.spaceMedium, y= spacing.spaceExtraSmall),
                        dropDownItems = state.priceItems?.map { it.title },
                        onSelect = { viewModel.onEvent(PostPriceUserEvent.OnPriceCategorySelect(it)) },
                        onDismissRequest = { viewModel.onEvent(PostPriceUserEvent.PriceCategoryDismiss) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            Text(
                modifier = placeholderModifier,
                text = ENTER_PRICE,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            PriceTextField(
                modifier = placeholderModifier
                    .fillMaxWidth(0.65f)
                    .heightIn(min = 50.dp),
                borderColor = state.priceTextFieldBorderColor,
                onClick = {
                    viewModel.onEvent(PostPriceUserEvent.PriceTextFieldClick)
                },
                value = state.price,
                onValueChange = {
                    viewModel.onEvent(PostPriceUserEvent.PriceChange(it))
                },
                enabled = state.priceTextFieldEnabled,
                onDone = {viewModel.onEvent(PostPriceUserEvent.KeyboardDone)},
                addedSymbol = " $CURRENCY_SYMBOL/${state.selectedPriceUnit}"
            )
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Text(
                    modifier = placeholderModifier,
                    text = "Konum: ${state.location}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                Icon(
                    modifier = placeholderModifier.size(spacing.spaceMedium),
                    imageVector = if(state.fineLocationPermissionGranted){
                        Icons.Outlined.CheckCircle
                    }else Icons.Outlined.LocationDisabled,
                    contentDescription = null,
                    tint = if(state.fineLocationPermissionGranted){
                        NoErrorGreen
                    }else MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                modifier = placeholderModifier,
                text = state.fineLocationPermissionRationale,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = if(state.fineLocationPermissionGranted){
                    NoErrorGreen
                }else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            MyGoogleMap(
                modifier = placeholderModifier
                    .fillMaxWidth()
                    .height(100.dp),
                latLng = state.myLatLng
            )
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            Button(
                modifier = placeholderModifier,
                onClick = { viewModel.onEvent(PostPriceUserEvent.PostPrice) },
                elevation = ButtonDefaults.buttonElevation(defaultElevation = spacing.defaultElevation),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {
                Text(text = SEND)
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                Icon(
                    modifier = Modifier.size(spacing.spaceMedium),
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
    if(state.showAlertDialog){
        CustomAlertDialog(
            priceText = "${state.price} " + "$CURRENCY_SYMBOL/${state.selectedPriceUnit} ",
            title = PRICE_WILL_BE_LISTED_AS_ARE_YOU_SURE,
            onDismissRequest = { viewModel.onEvent(PostPriceUserEvent.AlertDialogDismiss) },
            onConfirm = { viewModel.onEvent(PostPriceUserEvent.AlertDialogConfirm) },
            onDismiss = { viewModel.onEvent(PostPriceUserEvent.AlertDialogDismiss) }
        )
    }
    if(state.priceIsSent){
        DoneDialog(PRICE_POSTED){
            viewModel.onEvent(PostPriceUserEvent.DoneDialogDismiss)
        }
    }
}











