package com.commandiron.besonapp_clean_arch.domain.repository

import android.net.Uri
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.data.model.FirebaseUserProfile
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun signUp(email: String, password: String): Flow<Result<Unit>>
    suspend fun signIn(email: String, password: String): Flow<Result<Unit>>
    suspend fun signInWithCredential(idToken: String): Flow<Result<Unit>>
    fun signOut()
    suspend fun updateUserProfileToFirebaseDb(userProfile: UserProfile): Flow<Result<Unit>>
    suspend fun getUserProfileFromFirebaseDb(): Flow<Result<UserProfile?>>
    suspend fun getFirebaseCurrentUser(): Flow<FirebaseUser?>
    suspend fun uploadProfilePictureToFirebaseStorage(uri: Uri): Flow<Result<String>>
}