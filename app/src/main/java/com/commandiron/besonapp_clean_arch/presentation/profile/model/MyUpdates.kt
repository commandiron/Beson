package com.commandiron.besonapp_clean_arch.presentation.profile.model

data class MyUpdates(
    val itemId: Int,
    val title: String,
    val unit: String,
    val price: String,
    val location: String,
    var date: String
)