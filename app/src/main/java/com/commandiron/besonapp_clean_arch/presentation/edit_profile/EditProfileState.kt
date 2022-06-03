package com.commandiron.besonapp_clean_arch.presentation.edit_profile

import android.net.Uri
import com.commandiron.besonapp_clean_arch.domain.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.domain.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.domain.model.defaultConstructionItems

data class EditProfileState(
    val imageUrl: String = "",
    val newPictureUri: Uri? = null,
    val name: String = "",
    val phoneNumber: String = "",
    val selectedMainConstructionItem: MainConstructionItem? = defaultConstructionItems[1],
    val selectedSubConstructionItems: List<SubConstructionItem>? = defaultConstructionItems[1].subConstructionItems,
)
