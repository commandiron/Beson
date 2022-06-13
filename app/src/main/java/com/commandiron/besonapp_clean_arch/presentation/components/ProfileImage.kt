package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.ui.theme.MyPurple

@Composable
fun ProfileImage(
    imageUrl:String,
    size: Dp = 60.dp
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = imageUrl)
                .apply(
                    block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        error(R.drawable.ic_blank_profile_picture)
                        fallback(R.drawable.ic_blank_profile_picture)
                    }
                )
                .build()
        ),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(2.dp, MyPurple, CircleShape),
        contentScale = ContentScale.Crop
    )
}