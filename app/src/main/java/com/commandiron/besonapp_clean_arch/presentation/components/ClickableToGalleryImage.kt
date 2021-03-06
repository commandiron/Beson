package com.commandiron.besonapp_clean_arch.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.commandiron.besonapp_clean_arch.R
@Composable
fun ClickableToGalleryImage(
    imageUrl: String? = null,
    onImageChange:(Uri?) -> Unit = {}
){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uriFromGallery: Uri? ->
        onImageChange(uriFromGallery)
        imageUri = uriFromGallery
    }
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = if(imageUrl != null && imageUri == null) imageUrl else imageUri)
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
            .clickable {
                launcher.launch("image/*")
            }
            .size(100.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape),
        contentScale = ContentScale.Crop

    )
}