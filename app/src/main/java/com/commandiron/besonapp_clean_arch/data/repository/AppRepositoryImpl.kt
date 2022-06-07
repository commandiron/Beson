package com.commandiron.besonapp_clean_arch.data.repository

import android.net.Uri
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.data.mapper.toFirebaseUserProfile
import com.commandiron.besonapp_clean_arch.data.mapper.toUserProfile
import com.commandiron.besonapp_clean_arch.data.model.FirebaseUserProfile
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
): AppRepository {

    override suspend fun signUp(
        email: String, password: String
    ): Flow<Result<Unit>> = callbackFlow {
        send(Result.Loading())
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            if(it.user != null){
                trySend(Result.Success(Unit))
            }
        }.addOnFailureListener {
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<Result<Unit>> = callbackFlow{
        send(Result.Loading())
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            if(it.user != null){
                trySend(Result.Success(Unit))
            }
        }.addOnFailureListener {
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun signInWithCredential(idToken: String): Flow<Result<Unit>> = callbackFlow {
        send(Result.Loading())
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential).addOnSuccessListener {
            if(it.user != null){
                trySend(Result.Success(Unit))
            }
        }.addOnFailureListener {
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override fun signOut(){ auth.signOut() }

    override suspend fun updateUserProfileToFirebaseDb(
        userProfile: UserProfile
    ): Flow<Result<Unit>> = callbackFlow {
        send(Result.Loading())
        val userUID = auth.currentUser?.uid.toString()
        val databaseReference = firebaseDatabase.getReference("Profiles").child(userUID)
        databaseReference.setValue(userProfile.toFirebaseUserProfile()).addOnSuccessListener {
            trySend(Result.Success(Unit))
        }.addOnFailureListener {
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun getUserProfileFromFirebaseDb(
    ): Flow<Result<UserProfile?>> = callbackFlow {
        send(Result.Loading())
        val userUID = auth.currentUser?.uid.toString()
        val databaseReference = firebaseDatabase.getReference("Profiles").child(userUID)
        databaseReference.get().addOnSuccessListener {
            val firebaseUserProfile = it.getValue(FirebaseUserProfile::class.java)
            trySend(Result.Success(firebaseUserProfile?.toUserProfile()))
        }.addOnFailureListener{
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun getFirebaseCurrentUser(
    ): Flow<FirebaseUser?> = callbackFlow {
        auth.addAuthStateListener {
            trySend(it.currentUser)
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }

    override suspend fun uploadProfilePictureToFirebaseStorage(uri: Uri): Flow<Result<String>> = callbackFlow {
        send(Result.Loading())
        val uuidImage = UUID.randomUUID()
        val imageName = "images/$uuidImage.jpg"
        val storageRef = firebaseStorage.reference.child(imageName)
        storageRef.putFile(uri).addOnCompleteListener { task ->
            if(task.isSuccessful){
                storageRef.downloadUrl.addOnSuccessListener {
                    trySend(Result.Success(it.toString()))
                }
            }else{
                trySend(Result.Error(task.exception))
            }
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }
}


























