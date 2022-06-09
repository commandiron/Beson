package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.model.UserStatus
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GetUserStatus(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Flow<Result<UserStatus>> = callbackFlow {
        repository.getFirebaseCurrentUser().collect{ firebaseUser ->
            if(firebaseUser == null){
                trySend(Result.Success(UserStatus.SIGNED_OUT))
            }else{
                repository.getUserProfileFromFirebaseDb().collect{ result ->
                    when(result){
                        is Result.Loading-> {
                            trySend(Result.Loading())
                        }
                        is Result.Error -> {
                            trySend(Result.Error(result.exception))
                        }
                        is Result.Success -> {
                            when(result.data?.userType){
                                UserType.CUSTOMER -> {
                                    if(result.data.name.isNullOrBlank()){
                                        trySend(Result.Success(UserStatus.SIGNED_IN_UNFINISHED_PROFILE_CUSTOMER))
                                    }else{
                                        trySend(Result.Success(UserStatus.SIGNED_IN))
                                    }
                                }
                                UserType.COMPANY -> {
                                    if(result.data.name.isNullOrBlank()){
                                        trySend(Result.Success(UserStatus.SIGNED_IN_UNFINISHED_PROFILE_COMPANY))
                                    }else{
                                        trySend(Result.Success(UserStatus.SIGNED_IN))
                                    }
                                }
                                null -> {  trySend(Result.Success(UserStatus.SIGNED_IN_NO_SELECTION_CUSTOMER_OR_COMPANY)) }
                            }
                        }
                    }
                }
            }
        }
    }
}