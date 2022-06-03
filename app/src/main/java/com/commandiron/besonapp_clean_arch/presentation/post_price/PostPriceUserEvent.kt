package com.commandiron.besonapp_clean_arch.presentation.post_price

sealed class PostPriceUserEvent {
    object SubConsCategorySelectionBoxClick: PostPriceUserEvent()
    object SubConsCategoryDismiss: PostPriceUserEvent()
    data class OnSubConstructionCategorySelect(
        val selectedIndex: Int?): PostPriceUserEvent()
    object PriceCategorySelectionBoxClick: PostPriceUserEvent()
    object PriceCategoryDismiss: PostPriceUserEvent()
    data class OnPriceCategorySelect(
        val selectedIndex: Int?): PostPriceUserEvent()
    object PriceTextFieldClick: PostPriceUserEvent()
    data class PriceChange(val price: String): PostPriceUserEvent()
    object KeyboardDone: PostPriceUserEvent()
    object PostPrice: PostPriceUserEvent()
    object AlertDialogDismiss: PostPriceUserEvent()
    object AlertDialogConfirm: PostPriceUserEvent()
    object DoneDialogDismiss: PostPriceUserEvent()
}
