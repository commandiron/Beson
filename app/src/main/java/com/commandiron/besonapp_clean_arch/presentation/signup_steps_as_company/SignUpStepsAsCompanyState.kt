package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

import android.net.Uri
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems

data class SignUpStepsAsCompanyState(
    val name: String = "",
    val phoneNumber: String = "",
    val selectedPictureUri: Uri? = null,
    val profilePictureUrl: String? = null,
    val selectedMainConstructionItem: MainConstructionItem? = null,
    val selectedSubConstructionItems: List<SubConstructionItem>? = null,
    val mainConstructionItems: List<MainConstructionItem> = defaultConstructionItems,
)