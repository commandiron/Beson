package com.commandiron.besonapp_clean_arch.presentation.post_price

import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems

data class PostPriceState(

    val subConsItems: List<SubConstructionItem> = defaultConstructionItems[1].subConstructionItems,
    val priceItems: List<PriceItem>? = null,

    val subConsCategoryDropDownMenuIsExpanded: Boolean = false,
    val priceCategoryDropDownMenuIsExpanded: Boolean = false,

    val selectedSubConsItemTitle: String? = null,
    val selectedPriceItemTitle: String? = null,

    val price: String = ""
)