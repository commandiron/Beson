package com.commandiron.besonapp_clean_arch.domain.use_case

import android.net.Uri
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class UploadProfilePicture(
    private val repository: AppRepository
) {
    suspend operator fun invoke(uri: Uri): Flow<Result<String>> {
        return repository.uploadProfilePictureToFirebaseStorage(uri)
    }
}