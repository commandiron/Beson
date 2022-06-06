package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

import android.net.Uri
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem

sealed class SignUpStepsAsCompanyUserEvent {
    data class NameChanged(val name: String): SignUpStepsAsCompanyUserEvent()
    data class PhoneNumberChanged(val phoneNumber: String): SignUpStepsAsCompanyUserEvent()
    data class PictureChanged(val uri: Uri?): SignUpStepsAsCompanyUserEvent()
    data class MainCategorySelected(val itemMain: MainConstructionItem): SignUpStepsAsCompanyUserEvent()
    data class SubCategoriesSelected(val itemList: List<SubConstructionItem>?): SignUpStepsAsCompanyUserEvent()
    object NameScreenNext: SignUpStepsAsCompanyUserEvent()
    object PhoneNumberScreenNext: SignUpStepsAsCompanyUserEvent()
    object PictureScreenNext: SignUpStepsAsCompanyUserEvent()
    object ConstructionCategoryScreenNext: SignUpStepsAsCompanyUserEvent()
    object SubConstructionCategoryScreenNext: SignUpStepsAsCompanyUserEvent()
}