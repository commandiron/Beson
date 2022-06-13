package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem

class FilterSearchResults {
    operator fun invoke(
        query: String,
        selectedCategoryId: Int?,
        priceItems: List<PriceItem>
    ): List<PriceItem> {
        if (query.isEmpty()) {
            if (selectedCategoryId == null) {
                return priceItems.sortedByDescending { it.date }
            } else {
                return priceItems.filter {
                    it.priceCategoryId == selectedCategoryId
                }
            }
        } else {
            if (selectedCategoryId == null) {
                return priceItems.filter {
                    it.title.contains(query.trim(), ignoreCase = true) ||
                            it.location == query.trim() ||
                            it.userByName == query.trim()
                }
            } else {
                return priceItems
                    .filter {
                        it.priceCategoryId == selectedCategoryId
                    }.filter {
                    it.title.contains(query.trim(), ignoreCase = true) ||
                            it.location.contains(query.trim()) ||
                            it.userByName.contains(query.trim())
                    }
            }
        }
    }
}