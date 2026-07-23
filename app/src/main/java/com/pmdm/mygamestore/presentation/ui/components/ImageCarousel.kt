package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageCarousel(
    images: List<String>,
    modifier: Modifier = Modifier,
    height: Dp = 300.dp,
    showIndicators: Boolean = true
) {
    val pagerState = rememberPagerState(pageCount = { images.count() })

    Box(
        modifier = modifier.fillMaxWidth().height(height)
    ) {
        HorizontalPager(state = pagerState) {
            page -> AsyncImage(
                model = images[page],
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Imagen de carrusel"
            )
        }

        if (showIndicators && images.size > 1) {
            PageIndicator(pagerState, images.size, Modifier.align(Alignment.BottomCenter))
        }
    }
}