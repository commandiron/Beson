package com.commandiron.besonapp_clean_arch.data.repository

import android.net.Uri
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.data.mapper.toFirebasePriceItem
import com.commandiron.besonapp_clean_arch.data.mapper.toFirebaseUserProfile
import com.commandiron.besonapp_clean_arch.data.mapper.toPriceItem
import com.commandiron.besonapp_clean_arch.data.mapper.toUserProfile
import com.commandiron.besonapp_clean_arch.data.model.FirebasePriceItem
import com.commandiron.besonapp_clean_arch.data.model.FirebaseUserProfile
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
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
        }
    }

    override fun signOut(){ auth.signOut() }

    override suspend fun updateUserProfileToFirebaseDb(
        userProfile: UserProfile
    ): Flow<Result<Unit>> = callbackFlow {
        send(Result.Loading())
        val userUID = auth.currentUser?.uid.toString()
        val databaseReference = firebaseDatabase.getReference("Profiles").child(userUID)
        databaseReference
            .setValue(
                userProfile.toFirebaseUserProfile().copy(userUid = userUID)
            )
            .addOnSuccessListener {
                trySend(Result.Success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.Error(it))
            }
        awaitClose {
            channel.close()
        }
    }

    override suspend fun getUserProfileFromFirebaseDb(
    ): Flow<Result<UserProfile>> = callbackFlow {
        send(Result.Loading())
        val userUID = auth.currentUser?.uid.toString()
        val databaseReference = firebaseDatabase.getReference("Profiles").child(userUID)
        databaseReference.get().addOnSuccessListener { dataSnapshot ->
            val firebaseUserProfile = dataSnapshot.getValue(FirebaseUserProfile::class.java)
            firebaseUserProfile?.let {
                trySend(Result.Success(it.toUserProfile()))
            } ?: trySend(Result.Success(UserProfile()))
        }.addOnFailureListener{
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
        }
    }

    override suspend fun getFirebaseCurrentUser(
    ): Flow<FirebaseUser?> = callbackFlow {
        auth.addAuthStateListener {
            trySend(it.currentUser)
        }
        awaitClose {
            channel.close()
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
        }
    }

    override suspend fun postPriceToFirebase(priceItem: PriceItem): Flow<Result<Unit>> = callbackFlow {
        send(Result.Loading())
        val userUID = auth.currentUser?.uid.toString()
        val priceUID = UUID.randomUUID().toString()
        val databaseReference = firebaseDatabase.getReference("Prices").child(userUID).child(priceUID)
        databaseReference.setValue(priceItem.toFirebasePriceItem()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                trySend(Result.Success(Unit))
            }else{
                trySend(Result.Error(task.exception))
            }
        }
        awaitClose {
            channel.close()
        }
    }

    override suspend fun getMyPricesFromFirebase(): Flow<Result<List<PriceItem>?>> = callbackFlow {
        send(Result.Loading())
        val userUID = auth.currentUser?.uid.toString()
        val databaseReference = firebaseDatabase.getReference("Prices").child(userUID)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var priceItems = listOf<PriceItem>()
                snapshot.children.forEach { snapshotChild ->
                    val firebasePriceItem = snapshotChild.getValue(FirebasePriceItem::class.java)
                    firebasePriceItem?.let {
                        priceItems = priceItems + it.toPriceItem()
                    }
                }
                trySend(Result.Success(priceItems))
            }
            override fun onCancelled(error: DatabaseError) {
                trySend(Result.Error(Exception(error.message)))
            }
        })
        awaitClose {
            channel.close()
        }
    }

    override suspend fun getAllPricesFromFirebase(): Flow<Result<List<PriceItem>>> = callbackFlow {
        send(Result.Loading())
        val databaseReference = firebaseDatabase.getReference("Prices")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var priceItems = listOf<PriceItem>()
                snapshot.children.forEach { dataSnapshot ->
                    dataSnapshot.children.forEach { snapshotChild ->
                        val firebasePriceItem = snapshotChild.getValue(FirebasePriceItem::class.java)
                        firebasePriceItem?.let {
                            priceItems = priceItems + it.toPriceItem()
                        }
                    }
                }
                trySend(Result.Success(priceItems))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.Error(Exception(error.message)))
            }

        })
        awaitClose {
            channel.close()
        }
    }

    override suspend fun getUserProfileByIdFromFirebaseDb(userUid: String): Flow<Result<UserProfile>> = callbackFlow {
        send(Result.Loading())
        val databaseReference = firebaseDatabase.getReference("Profiles").child(userUid)
        databaseReference.get().addOnSuccessListener { dataSnapshot ->
            val firebaseUserProfile = dataSnapshot.getValue(FirebaseUserProfile::class.java)
            firebaseUserProfile?.let {
                trySend(Result.Success(it.toUserProfile()))
            } ?: trySend(Result.Success(UserProfile()))
        }.addOnFailureListener{
            trySend(Result.Error(it))
        }
        awaitClose {
            channel.close()
        }
    }
}


























