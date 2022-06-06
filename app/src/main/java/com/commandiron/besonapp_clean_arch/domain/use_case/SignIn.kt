package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class SignIn(
    private val repository: AppRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<Unit>> {
        return repository.signIn(email, password)
    }
}