package com.commandiron.besonapp_clean_arch.presentation.intro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.domain.model.IntroScreenElement
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

//@Composable
//fun CustomHorizontalPager(
//    introElements: List<IntroScreenElement>
//) {
//    val pagerState = rememberPagerState()
//    HorizontalPager(
//        count = introElements.size,
//        state = pagerState,
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        modifier = Modifier
//            .fillMaxWidth(),
//    ) { numberOfPage->
//        PagerItem(
//            introScreenElementList = introElements,
//            numberOfPage = numberOfPage
//        )
//        lastPageFlag = pagerState.currentPage == listOfIntroElement.size - 1
//    }
//    Spacer(modifier = Modifier.height(20.dp))
//    HorizontalPagerIndicator(
//        pagerState = pagerState,
//        activeColor = if(lastPageFlag) primaryVariantColorNoTheme else primaryColorNoTheme,
//        inactiveColor = if(lastPageFlag) primaryVariantColorNoTheme.copy(0.5f) else primaryColorNoTheme.copy(0.5f)
//    )
//}
//@Composable
//fun PagerItem(
//    introScreenElementList: List<IntroScreenElement>,
//    numberOfPage: Int,
//) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            modifier = Modifier.size(260.dp,548.dp),
//            painter = rememberImagePainter(
//                data = introScreenElementList[numberOfPage].imageResource,
//            ),
//            contentScale = ContentScale.Fit,
//            contentDescription = null
//        )
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = introScreenElementList[numberOfPage].explanationText,
//                color = MaterialTheme.colors.onBackground,
//                style = MaterialTheme.typography.body2,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}