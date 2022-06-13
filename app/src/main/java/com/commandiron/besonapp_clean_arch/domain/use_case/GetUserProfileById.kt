package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import kotlinx.coroutines.flow.Flow

class GetUserProfileById(
    private val repository: AppRepository
) {
    suspend operator fun invoke(userUid: String): Flow<Result<UserProfile>> {
        return repository.getUserProfileByIdFromFirebaseDb(userUid)
    }
}