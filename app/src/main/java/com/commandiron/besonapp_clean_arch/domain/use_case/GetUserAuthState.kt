package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.model.UserState
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetUserAuthState(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Flow<UserState> {
        return repository.getUserAuthState()
    }
}