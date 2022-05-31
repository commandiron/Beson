package com.commandiron.besonapp_clean_arch.presentation.post_price

sealed class PostPriceUserEvent {
    object OnSubConstructionCategoryBoxClick: PostPriceUserEvent()
    object OnSubConstructionCategoryDismiss: PostPriceUserEvent()
    data class OnSubConstructionCategorySelect(
        val selectedIndex: Int?): PostPriceUserEvent()
    object OnPriceCategoryBoxClick: PostPriceUserEvent()
    object OnPriceCategoryDismiss: PostPriceUserEvent()
    data class OnPriceCategorySelect(
        val selectedIndex: Int?): PostPriceUserEvent()
}
