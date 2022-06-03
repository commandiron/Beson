package com.commandiron.besonapp_clean_arch.domain.repository

import com.commandiron.besonapp_clean_arch.core.NetworkResult
import com.commandiron.besonapp_clean_arch.domain.model.UserState
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun signUp(email: String, password: String): Flow<NetworkResult<Unit>>
    suspend fun signIn(email: String, password: String): Flow<NetworkResult<Unit>>
    fun signOut()
    suspend fun getUserAuthState(): Flow<UserState>
}