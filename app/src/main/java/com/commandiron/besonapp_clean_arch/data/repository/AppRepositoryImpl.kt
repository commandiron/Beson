package com.commandiron.besonapp_clean_arch.data.repository

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.data.mapper.toFirebaseUserProfile
import com.commandiron.besonapp_clean_arch.data.mapper.toUserProfile
import com.commandiron.besonapp_clean_arch.data.model.FirebaseUserProfile
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
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

    override suspend fun signIn(
        email: String, password: String
    ): Flow<Result<Unit>> = callbackFlow{
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
}