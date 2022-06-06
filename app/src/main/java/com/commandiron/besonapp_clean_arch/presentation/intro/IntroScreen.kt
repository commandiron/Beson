package com.commandiron.besonapp_clean_arch.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.intro.components.CustomHorizontalPager
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSystemUiController
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun IntroScreen(
    viewModel: IntroViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {
    val spacing = LocalSpacing.current
    val systemUiController = LocalSystemUiController.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.NavigateTo -> navigateTo(event.route)
                else -> {}
            }
        }
    }
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.background
    )
    systemUiController.setNavigationBarColor(
        color = state.footerColor,
        darkIcons = true
    )
    Column(
        modifier = Modifier
            .padding(top = spacing.spaceExtraLarge)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val pagerState = rememberPagerState()
        CustomHorizontalPager(
            pagerState = pagerState,
            introElements = state.introElements,
            onSwipe = {
                viewModel.onEvent(
                    IntroUserEvent.OnSwipe(
                        lastPageFlag = pagerState.currentPage == state.introElements.size - 1
                    )
                )
            }
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor =  state.footerColor,
            inactiveColor = state.footerColor.copy(0.5f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacing.navigationHeight)
                .clickable {
                    viewModel.onEvent(IntroUserEvent.OnStartClick)
                }
                .background(state.footerColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.bottomBarText,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
