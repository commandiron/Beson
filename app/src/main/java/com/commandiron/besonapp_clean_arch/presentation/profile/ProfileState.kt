package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.presentation.profile.model.MyUpdates
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile

data class ProfileState(
    val name: String = "",
    val imageUrl: String? = null,
    val profileHeaderHeight: Dp = 0.dp,
    val myUpdatesSurfaceExpanded: Boolean = false,
    val myFavoriteProfilesSurfaceExpanded: Boolean = false,
    val myUpdates: List<MyUpdates> = listOf(
        MyUpdates(itemId = 0, title = "title 1", unit = "m2", price = "1250", location = "İstanbul", date = "30.10.2022"),
        MyUpdates(itemId = 1, title = "title 2", unit = "m2", price = "1450", location = "İstanbul", date = "12.10.2022"),
        MyUpdates(itemId = 2, title = "title 3", unit = "m2", price = "5650", location = "İstanbul", date = "23.10.2022"),
        MyUpdates(itemId = 3, title = "title 4", unit = "m2", price = "230", location = "İstanbul", date = "14.10.2022"),
        MyUpdates(itemId = 0, title = "title 1", unit = "m2", price = "1250", location = "İstanbul", date = "30.10.2022"),
        MyUpdates(itemId = 1, title = "title 2", unit = "m2", price = "1450", location = "İstanbul", date = "12.10.2022"),
        MyUpdates(itemId = 2, title = "title 3", unit = "m2", price = "5650", location = "İstanbul", date = "23.10.2022"),
        MyUpdates(itemId = 3, title = "title 4", unit = "m2", price = "230", location = "İstanbul", date = "14.10.2022")
    ),
    val favoriteUserProfiles: List<UserProfile> = listOf(
        UserProfile(name = "Emir Demirli", phoneNumber = "+905355085552"),
        UserProfile(name = "Emir Demirli", phoneNumber = "+905357227406"),
        UserProfile(name = "Yeşim Demirli", phoneNumber = "+905424129016")
    ),
    val showDeleteMyUpdateAlertDialog: Boolean = false,
    val showUnFavoriteAlertDialog: Boolean = false,
    val isLoading: Boolean = false,
    val loadingMessage: String = "",
    val showDoneDialog: Boolean = false,
    val doneDialogMessage: String = "",
)