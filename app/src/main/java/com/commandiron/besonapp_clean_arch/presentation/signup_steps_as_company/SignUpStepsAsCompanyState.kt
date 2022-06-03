package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

import android.net.Uri
import com.commandiron.besonapp_clean_arch.domain.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.domain.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.domain.model.defaultConstructionItems

data class SignUpStepsAsCompanyState(
    val name: String = "",
    val phoneNumber: String = "",
    val profilePictureUri: Uri? = null,
    val selectedMainConstructionItem: MainConstructionItem? = null,
    val selectedSubConstructionItems: List<SubConstructionItem>? = null,
    val mainConstructionItems: List<MainConstructionItem> = defaultConstructionItems
)