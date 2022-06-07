package com.commandiron.besonapp_clean_arch.presentation.model

import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

data class UserProfile(
    val name: String? = null,
    val phoneNumber: String? = null,
    val imageUrl: String? = null,
    val userType: UserType? = null,
    val selectedMainConstructionItem: MainConstructionItem? = null,
    val selectedSubConstructionItems: List<SubConstructionItem>? = null
)
