package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AddToFavorites(
    private val repository: AppRepository
) {
    suspend operator fun invoke(userUid: String): Flow<Result<Unit>> {
        return repository.addUserToFavoritesInFirebase(userUid)
    }
}