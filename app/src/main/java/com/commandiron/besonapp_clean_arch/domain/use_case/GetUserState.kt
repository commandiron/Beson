package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.presentation.model.UserState
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserState(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Flow<Result<UserState>> = flow {
        repository.getFirebaseCurrentUser().collect{ firebaseUser ->
            if(firebaseUser == null){
                emit(Result.Success(UserState.SIGNED_OUT))
            }else{
                repository.getUserProfileFromFirebaseDb().collect{ result ->
                    when(result){
                        is Result.Loading-> {
                            emit(Result.Loading())
                        }
                        is Result.Error -> {
                            emit(Result.Error(result.exception))
                        }
                        is Result.Success -> {
                            val userProfile = result.data

                            when(userProfile?.userType){
                                UserType.CUSTOMER -> {
                                    if(result.data.name.isNullOrBlank()){
                                        emit(Result.Success(UserState.SIGNED_IN_UNFINISHED_PROFILE_CUSTOMER))
                                    }else{
                                        emit(Result.Success(UserState.SIGNED_IN))
                                    }
                                }
                                UserType.COMPANY -> {
                                    println(result.data)
                                    if(result.data.name.isNullOrBlank()){
                                        emit(Result.Success(UserState.SIGNED_IN_UNFINISHED_PROFILE_COMPANY))
                                    }else{
                                        emit(Result.Success(UserState.SIGNED_IN))
                                    }
                                }
                                null -> { emit(Result.Success(UserState.SIGNED_IN_NO_SELECTION_CUSTOMER_OR_COMPANY)) }
                            }
                        }
                    }
                }
            }
        }
    }
}