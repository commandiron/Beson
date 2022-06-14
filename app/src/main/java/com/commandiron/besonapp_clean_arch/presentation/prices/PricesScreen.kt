package com.commandiron.besonapp_clean_arch.presentation.prices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.model.defaultSubConstructionItems
import com.commandiron.besonapp_clean_arch.presentation.post_price.PostPriceUserEvent
import com.commandiron.besonapp_clean_arch.presentation.prices.components.HorizontalCategorySelector
import com.commandiron.besonapp_clean_arch.presentation.prices.components.PriceMenuItem
import com.commandiron.besonapp_clean_arch.presentation.prices.components.SearchTextField
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PricesScreen(
    viewModel: PricesViewModel = hiltViewModel(),
    showSnackbar:(String) -> Unit,
    navigateTo:(NavigationOptions) -> Unit,
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackbar -> showSnackbar(event.message)
                is UiEvent.NavigateTo -> navigateTo(event.navigationOptions)
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
    val placeholderModifier = Modifier.placeholder(
        visible = state.isLoading,
        highlight = PlaceholderHighlight.shimmer()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.spaceLarge)
    ){
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            text = state.searchText,
            hint = "Search",
            onChange = {
                viewModel.onEvent(PricesUserEvent.SearchChange(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        HorizontalCategorySelector(
            itemList = defaultSubConstructionItems,
            onItemClick = {
                viewModel.onEvent(PricesUserEvent.CategoryClick(it))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        state.filteredPriceItems?.let {
            val swipeRefreshState = rememberSwipeRefreshState(state.isLoading)
            SwipeRefresh(
                modifier = Modifier.padding(bottom = spacing.navigationHeight),
                refreshTriggerDistance = spacing.spaceXXLarge,
                state = swipeRefreshState,
                onRefresh = { viewModel.onEvent(PricesUserEvent.SwipeRefresh) }
            ) {
                LazyColumn() {
                    items(it){ item ->
                        PriceMenuItem(
                            modifier = placeholderModifier,
                            item = item,
                            onDetailClick = {
                                viewModel.onEvent(PricesUserEvent.DetailClick(it))
                            }
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    }
                    item {
                        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
                    }
                }
            }
        }
    }
}















