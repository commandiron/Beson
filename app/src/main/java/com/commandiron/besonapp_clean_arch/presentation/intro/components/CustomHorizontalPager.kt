package com.commandiron.besonapp_clean_arch.presentation.intro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.commandiron.besonapp_clean_arch.presentation.intro.model.IntroElement
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@Composable
fun CustomHorizontalPager(
    pagerState: PagerState,
    introElements: List<IntroElement>,
    onSwipe:() -> Unit
) {
    val spacing = LocalSpacing.current
    HorizontalPager(
        count = introElements.size,
        modifier = Modifier
            .fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = spacing.spaceMedium),
    ) { numberOfPage->
        LaunchedEffect(key1 = pagerState.currentPage){
            onSwipe()
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = introElements[numberOfPage].imageResource
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(540.dp)
            )
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            Text(
                text = introElements[numberOfPage].explanationText,
                modifier = Modifier
                    .padding(horizontal = spacing.spaceLarge),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}