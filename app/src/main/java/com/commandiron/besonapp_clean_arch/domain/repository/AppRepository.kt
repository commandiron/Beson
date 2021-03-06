package com.commandiron.besonapp_clean_arch.domain.repository

import android.net.Uri
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.presentation.model.FavoriteStatus
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun signUp(email: String, password: String): Flow<Result<Unit>>
    suspend fun signIn(email: String, password: String): Flow<Result<Unit>>
    suspend fun signInWithCredential(idToken: String): Flow<Result<Unit>>
    fun signOut()
    suspend fun updateUserProfileToFirebaseDb(userProfile: UserProfile): Flow<Result<Unit>>
    suspend fun getUserProfileFromFirebaseDb(): Flow<Result<UserProfile>>
    suspend fun getFirebaseCurrentUser(): Flow<FirebaseUser?>
    suspend fun uploadProfilePictureToFirebaseStorage(uri: Uri): Flow<Result<String>>
    suspend fun postPriceToFirebase(priceItem: PriceItem): Flow<Result<Unit>>
    suspend fun getMyPricesFromFirebase(): Flow<Result<List<PriceItem>?>>
    suspend fun getAllPricesFromFirebase(): Flow<Result<List<PriceItem>>>
    suspend fun getUserProfileByIdFromFirebaseDb(userUid:String): Flow<Result<UserProfile>>
    suspend fun deleteMyPriceFromFirebase(priceItem: PriceItem): Flow<Result<Unit>>
    suspend fun addUserToFavoritesInFirebase(userUid: String): Flow<Result<Unit>>
    suspend fun getUserFavoriteStatusFromFirebase(userUid: String): Flow<Result<FavoriteStatus>>
    suspend fun removeUserFromFavoritesInFirebase(userUid: String): Flow<Result<Unit>>
    suspend fun getAllMyFavoritesUserUidFromFirebase(): Flow<Result<List<UserProfile>>>
}