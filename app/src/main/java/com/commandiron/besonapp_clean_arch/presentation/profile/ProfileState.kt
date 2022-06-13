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
    val favoriteUserProfiles: List<UserProfile> = listOf(
        UserProfile(name = "Emir Demirli", phoneNumber = "+905355085552"),
        UserProfile(name = "Emir Demirli", phoneNumber = "+905357227406"),
        UserProfile(name = "Yeşim Demirli", phoneNumber = "+905424129016")
    ),
    val showDeleteMyUpdateAlertDialog: Boolean = false,
    val showUnFavoriteAlertDialog: Boolean = false,
    val showDoneDialog: Boolean = false,
    val doneDialogMessage: String = "",
)