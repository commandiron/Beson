package com.commandiron.besonapp_clean_arch.data.model

data class FirebaseUserProfile(
    val name: String = "",
    val phoneNumber: String =  "",
    val imageUrl: String = "",
    val userType: String = "CUSTOMER",
    val selectedMainConstructionItemId: Int? = null,
    val selectedSubConstructionItemIds: List<Int>? = null,
)