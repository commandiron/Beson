package com.commandiron.besonapp_clean_arch.presentation.prices

import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultSubConstructionItems

data class PricesState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val subConstructionItems: List<SubConstructionItem> = defaultSubConstructionItems,
    val selectedCategoryId: Int? = null,
    val allPriceItems: List<PriceItem>? = null,
    val filteredPriceItems: List<PriceItem>? = null
)
