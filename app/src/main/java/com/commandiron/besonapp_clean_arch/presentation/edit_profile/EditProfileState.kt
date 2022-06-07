package com.commandiron.besonapp_clean_arch.presentation.edit_profile

import android.net.Uri
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

data class EditProfileState(
    val name: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
    val userType: UserType? = null,
    val selectedMainConstructionItem: MainConstructionItem? = null,
    val selectedSubConstructionItems: List<SubConstructionItem>? = null,
    val newPictureUri: Uri? = null,
    val isLoading: Boolean = false
)
