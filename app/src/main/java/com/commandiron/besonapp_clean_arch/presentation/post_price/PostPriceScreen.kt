package com.commandiron.besonapp_clean_arch.presentation.post_price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.presentation.post_price.components.CustomDropDown
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun PostPriceScreen(
    viewModel: PostPriceViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = "Kategori Seç",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        CustomDropDown(
            onCategoryBoxClick = { viewModel.onEvent(PostPriceUserEvent.OnSubConstructionCategoryBoxClick) },
            title = state.selectedSubConsItemTitle ?: "Seçiniz",
            isExpanded = state.subConsCategoryDropDownMenuIsExpanded,
            dropDownItems = state.subConsItems.map { it.title },
            onSelect = { viewModel.onEvent(PostPriceUserEvent.OnSubConstructionCategorySelect(it)) },
            onDismissRequest = { viewModel.onEvent(PostPriceUserEvent.OnSubConstructionCategoryDismiss) }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = "Fiyat Kategorisi Seç",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        CustomDropDown(
            onCategoryBoxClick = { viewModel.onEvent(PostPriceUserEvent.OnPriceCategoryBoxClick) },
            title = state.selectedPriceItemTitle ?: "Seçiniz",
            isExpanded = state.priceCategoryDropDownMenuIsExpanded,
            dropDownItems = state.priceItems?.map { it.title },
            onSelect = { viewModel.onEvent(PostPriceUserEvent.OnPriceCategorySelect(it)) },
            onDismissRequest = { viewModel.onEvent(PostPriceUserEvent.OnPriceCategoryDismiss) }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = "Fiyatı Gir",
            style = MaterialTheme.typography.titleMedium
        )
    }
}