package com.commandiron.besonapp_clean_arch.data.mapper

import com.commandiron.besonapp_clean_arch.data.model.FirebaseUserProfile
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

fun UserProfile.toFirebaseUserProfile(): FirebaseUserProfile {
    return FirebaseUserProfile(
        name = name,
        phoneNumber = phoneNumber,
        imageUrl = imageUrl,
        userType = userType.toString(),
        selectedMainConstructionItemId = selectedMainConstructionItem?.id,
        selectedSubConstructionItemIds = selectedSubConstructionItems?.map { it.id }
    )
}

fun FirebaseUserProfile.toUserProfile(): UserProfile {
    return UserProfile(
        name = name,
        phoneNumber = phoneNumber,
        imageUrl = imageUrl,
        userType = UserType.valueOf(userType),
        selectedMainConstructionItem = defaultConstructionItems[selectedMainConstructionItemId ?: 0],
        selectedSubConstructionItems =
        defaultConstructionItems[selectedMainConstructionItemId ?: 0]
            .subConstructionItems
            .filter { selectedSubConstructionItemIds?.contains(it.id) ?: false }
    )
}