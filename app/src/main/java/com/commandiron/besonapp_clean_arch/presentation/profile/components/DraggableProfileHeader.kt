package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.presentation.components.toDp
import com.commandiron.besonapp_clean_arch.ui.theme.NoRippleTheme

@Composable
fun DraggableProfileHeader(
    text: String,
    imageUrl: String? = null,
    onEditButtonClick:() -> Unit,
    onVerticalDrag:(Dp) -> Unit
){
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    var progressWithDrag by remember { mutableStateOf(0f) } //Between 0.0 and 1.0 72px to 182px

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progressWithDrag,
        modifier = Modifier.fillMaxWidth()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .layoutId("box")
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onVerticalDrag = { _, dragAmount ->
                            val newValue = dragAmount.coerceIn(-50.dp.toPx(), 50.dp.toPx())
                            if (newValue > 0) {
                                if (progressWithDrag < 1.0) {

                                    progressWithDrag += newValue / 364

                                    if (progressWithDrag > 1.0) {
                                        progressWithDrag = 1.0f
                                    }
                                }
                            } else if (newValue < 0) {
                                if (progressWithDrag > 0) {

                                    progressWithDrag += newValue / 364

                                    if (progressWithDrag < 0.0) {
                                        progressWithDrag = 0f
                                    }
                                }
                            }
                        }
                    )
                },
            contentAlignment = Alignment.TopEnd
        ){
            onVerticalDrag(this.constraints.minHeight.toDp())
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme){
                IconButton(
                    modifier = Modifier
                        .padding(top = 26.dp, bottom = 16.dp, end = 36.dp)
                        .size(20.dp),
                    onClick = { onEditButtonClick() }
                ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = null
                    )
                }
            }
        }
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
                .layoutId("profile_pic")
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = text,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.layoutId("username")
        )
    }
}