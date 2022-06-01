package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import kotlinx.coroutines.delay

class PushPrice(
    private val repository: AppRepository
){
    suspend operator fun invoke(): Result<String>{
        delay(7000)
        return Result.success("okay")
    }
}