package com.commandiron.besonapp_clean_arch.presentation.post_price

import com.commandiron.besonapp_clean_arch.domain.model.PriceItem
import com.commandiron.besonapp_clean_arch.domain.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.domain.model.defaultConstructionItems

data class PostPriceState(

    val subConsItems: List<SubConstructionItem> = defaultConstructionItems[1].subConstructionItems,
    val priceItems: List<PriceItem>? = null,

    val subConsCategoryDropDownMenuIsExpanded: Boolean = false,
    val priceCategoryDropDownMenuIsExpanded: Boolean = false,

    val selectedSubConsItemTitle: String? = null,
    val selectedPriceItemTitle: String? = null,

    val priceTextFieldEnabled: Boolean = false,
    val selectedPriceItemUnit: String? = null,
    val price: String = "",

    val showAlertDialog: Boolean = false,
    val placeholderIsVisible: Boolean = false,
    val isLoading: Boolean = false,
    val priceIsSent: Boolean = false,
)
