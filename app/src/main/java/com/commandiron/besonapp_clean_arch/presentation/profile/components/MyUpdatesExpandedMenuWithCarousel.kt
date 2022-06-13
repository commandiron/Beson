package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.presentation.components.carousel.Carousel
import com.commandiron.besonapp_clean_arch.presentation.components.carousel.CarouselDefaults
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.ui.theme.CarouselColor
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun MyUpdatesExpandedMenuWithCarousel(
    height: Dp,
    myPrices: List<PriceItem>,
    onDelete:(PriceItem) -> Unit
) {
    val spacing = LocalSpacing.current
    val lazyListState = rememberLazyListState()
    Spacer(modifier = Modifier.height(spacing.spaceMedium))
    Box{
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = height)
                .padding(end = spacing.spaceMedium)
        ){
            items(myPrices){ item ->
                MyPriceMenuItem(item = item, onDelete = onDelete)
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
            }
        }
        Box(
            modifier  = Modifier
                .fillMaxWidth()
                .heightIn(max = height)
                .padding(
                    vertical = spacing.spaceMedium,
                    horizontal = (spacing.spaceMedium - 2.dp) / 2
                ),
            contentAlignment = Alignment.CenterEnd
        ) {
            Carousel(
                state = lazyListState,
                modifier = Modifier
                    .size(2.dp, height)
                    .align(Alignment.CenterEnd),
                colors = CarouselDefaults.colors(thumbColor = CarouselColor)
            )
        }
    }
}