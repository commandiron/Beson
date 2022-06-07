package com.commandiron.besonapp_clean_arch.data.model

data class FirebaseUserProfile(
    val name: String? = null,
    val phoneNumber: String? =  null,
    val imageUrl: String? = null,
    val userType: String? = null,
    val selectedMainConstructionItemId: Int? = null,
    val selectedSubConstructionItemIds: List<Int>? = null,
)