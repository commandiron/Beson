package com.commandiron.besonapp_clean_arch.presentation.prices

import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem

sealed class PricesUserEvent {
    data class SearchChange(val text: String): PricesUserEvent()
    data class CategoryClick(val id: Int?): PricesUserEvent()
    data class DetailClick(val item: PriceItem): PricesUserEvent()
    object SwipeRefresh: PricesUserEvent()
}
