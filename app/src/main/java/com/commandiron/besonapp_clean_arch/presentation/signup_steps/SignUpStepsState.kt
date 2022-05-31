package com.commandiron.besonapp_clean_arch.presentation.signup_steps

import android.net.Uri
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems

data class SignUpStepsState(
    val name: String = "",
    val phoneNumber: String = "",
    val profilePictureUri: Uri? = null,
    val selectedMainConstructionItem: MainConstructionItem? = null,
    val selectedSubConstructionItems: List<SubConstructionItem>? = null,
    val mainConstructionItems: List<MainConstructionItem> = defaultConstructionItems
)