package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.NetworkResult
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class SignUp(
    private val repository: AppRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<NetworkResult<Unit>> {
        return repository.signUp(email, password)
    }
}