package com.commandiron.besonapp_clean_arch.presentation.profile

import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

data class ProfileState(
    val name: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
    val userType: UserType = UserType.CUSTOMER,
    val myUpdatesSurfaceExpanded: Boolean = false,
    val myFavoriteProfilesSurfaceExpanded: Boolean = false,
    val myPrices: List<PriceItem>? = null,
    val favoriteUserProfiles: List<UserProfile>? = null,
    val showDeleteMyUpdateAlertDialog: Boolean = false,
    val showUnFavoriteAlertDialog: Boolean = false,
    val showDoneDialog: Boolean = false,
    val doneDialogMessage: String = "",
    val deletedPriceItem: PriceItem? = null,
    val deletedFavoriteProfileUid: String? = null
)