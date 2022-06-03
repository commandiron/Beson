package com.commandiron.besonapp_clean_arch.domain.model

data class PriceItem(
    val itemId: Int,
    val title: String,
    val unit: String,
    val price: Double? = null,
    val location: String? = null,
    var date: Long? = null,
    var userById: Int? = null,
)
