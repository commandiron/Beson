package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import kotlinx.coroutines.flow.Flow

class GetMyPrices(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Flow<Result<List<PriceItem>?>> {
        return repository.getMyPricesFromFirebase()
    }
}