package com.commandiron.besonapp_clean_arch.data.repository

import com.commandiron.besonapp_clean_arch.core.NetworkResult
import com.commandiron.besonapp_clean_arch.domain.model.UserState
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AppRepository {

    override suspend fun signUp(email: String, password: String): Flow<NetworkResult<Unit>> = callbackFlow {
        trySend(NetworkResult.Loading())
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            if(it.user != null){
                trySend(NetworkResult.Success(Unit))
            }
        }.addOnFailureListener {
            trySend(NetworkResult.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<NetworkResult<Unit>> = callbackFlow{
        trySend(NetworkResult.Loading())
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            if(it.user != null){
                trySend(NetworkResult.Success(Unit))
            }
        }.addOnFailureListener {
            trySend(NetworkResult.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override fun signOut(){ auth.signOut() }

    override suspend fun getUserAuthState(): Flow<UserState> = callbackFlow {
        auth.addAuthStateListener {
            if(it.currentUser != null){
                trySend(UserState.SIGNED_IN)
            }else{
                trySend(UserState.SIGNED_OUT)
            }
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }
}