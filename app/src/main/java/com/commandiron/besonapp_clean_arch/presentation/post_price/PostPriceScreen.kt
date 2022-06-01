package com.commandiron.besonapp_clean_arch.presentation.post_price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_WILL_BE_SENT_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.*
import com.commandiron.besonapp_clean_arch.presentation.post_price.event.PostPriceUiEvent
import com.commandiron.besonapp_clean_arch.presentation.post_price.event.PostPriceUserEvent
import com.commandiron.besonapp_clean_arch.ui.theme.LocalCoroutineScope
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSnackbarHostState
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.Orange
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.launch

@Composable
fun PostPriceScreen(
    viewModel: PostPriceViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = LocalCoroutineScope.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is PostPriceUiEvent.CloseKeyboard -> {
                    keyboardController?.hide()
                }
                is PostPriceUiEvent.ShowSnackBar -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message, "Gizle")
                    }
                }
            }
        }
    }
    val placeHolderModifier = Modifier.placeholder(
        visible = state.placeholderIsVisible,
        highlight = if(state.priceIsSent) null else PlaceholderHighlight.shimmer()
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.primary)
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
                        modifier = placeHolderModifier,
                        text = "Kategori Seç",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    CustomDropDown(
                        modifier = placeHolderModifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp),
                        onCategoryBoxClick = { viewModel.onEvent(PostPriceUserEvent.SubConsCategorySelectionBoxClick) },
                        title = state.selectedSubConsItemTitle ?: "Seçiniz",
                        isExpanded = state.subConsCategoryDropDownMenuIsExpanded,
                        offset = DpOffset(x= spacing.spaceMedium, y= spacing.spaceExtraSmall),
                        dropDownItems = state.subConsItems.map { it.title },
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
                        modifier = placeHolderModifier,
                        text = "Fiyat Kategorisi Seç",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    CustomDropDown(
                        modifier = placeHolderModifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp),
                        onCategoryBoxClick = { viewModel.onEvent(PostPriceUserEvent.PriceCategorySelectionBoxClick) },
                        title = state.selectedPriceItemTitle ?: "Seçiniz",
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
                modifier = placeHolderModifier,
                text = "Fiyatı Gir",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            PriceTextField(
                modifier = placeHolderModifier
                    .fillMaxWidth(0.65f)
                    .heightIn(min = 50.dp),
                onClick = {
                    viewModel.onEvent(PostPriceUserEvent.PriceTextFieldClick)
                },
                value = state.price,
                onValueChange = {
                    viewModel.onEvent(PostPriceUserEvent.PriceChange(it))
                },
                enabled = state.priceTextFieldEnabled,
                onDone = {viewModel.onEvent(PostPriceUserEvent.KeyboardDone)},
                addedSymbol = " TL/${state.selectedPriceItemUnit}"
            )
            Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
            Button(
                modifier = placeHolderModifier,
                onClick = { viewModel.onEvent(PostPriceUserEvent.PostPrice) },
                elevation = ButtonDefaults.buttonElevation(defaultElevation = spacing.defaultElevation),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {
                Text(text = "Gönder!")
            }
        }
    }
    if(state.showAlertDialog){
        CustomAlertDialog(
            title = PRICE_WILL_BE_SENT_ARE_YOU_SURE,
            onDismissRequest = { viewModel.onEvent(PostPriceUserEvent.AlertDialogDismiss) },
            onConfirm = { viewModel.onEvent(PostPriceUserEvent.AlertDialogConfirm) },
            onDismiss = { viewModel.onEvent(PostPriceUserEvent.AlertDialogDismiss) }
        )
    }
    if(state.isLoading){
        LinearProgressLoadingScreen(title = "Fiyat Gönderiliyor")
    }
    if(state.priceIsSent){
        PriceSentDialog{
            viewModel.onEvent(PostPriceUserEvent.PriceSentDialogDismiss)
        }
    }
}











