package com.commandiron.besonapp_clean_arch.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.intro.components.CustomHorizontalPager
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun IntroScreen(
    systemUiController: SystemUiController,
    onNextClick:() -> Unit,
    viewModel: IntroViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.onBackground
    )
    systemUiController.setNavigationBarColor(
        color = state.footerColor
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
                    IntroEvent.OnSwipe(
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
                    viewModel.onEvent(IntroEvent.OnStartClick)
                }
                .background(state.footerColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.bottomBarText,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}
