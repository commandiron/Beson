package com.commandiron.besonapp_clean_arch.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.TELEPHONE_NUMBER_WITH_TURKEY_PHONE_CODE
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.Orange

@Composable
fun ProfileMenuItem(
    userProfile: UserProfile,
    unFavorite:(String) -> Unit
) {
    val spacing = LocalSpacing.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(6f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = userProfile.imageUrl)
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
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop

                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Column {
                    Text(
                        text = userProfile.name ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = TELEPHONE_NUMBER_WITH_TURKEY_PHONE_CODE + userProfile.phoneNumber,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .clickable { unFavorite(userProfile.userUid ?: "") },
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Orange
            )

        }
    }
}