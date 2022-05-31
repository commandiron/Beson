package com.commandiron.besonapp_clean_arch.presentation.signup_steps

import android.net.Uri
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem

sealed class SignUpStepsUserEvent {
    object OnBackToSignUpClick: SignUpStepsUserEvent()
    data class NameChanged(val name: String): SignUpStepsUserEvent()
    data class PhoneNumberChanged(val phoneNumber: String): SignUpStepsUserEvent()
    data class PictureChanged(val uri: Uri?): SignUpStepsUserEvent()
    data class MainCategorySelected(val itemMain: MainConstructionItem): SignUpStepsUserEvent()
    data class SubCategoriesSelected(val itemList: List<SubConstructionItem>?): SignUpStepsUserEvent()
    object NameScreenNext: SignUpStepsUserEvent()
    object PhoneNumberScreenNext: SignUpStepsUserEvent()
    object PictureScreenNext: SignUpStepsUserEvent()
    object ConstructionCategoryScreenNext: SignUpStepsUserEvent()
    object SubConstructionCategoryScreenNext: SignUpStepsUserEvent()
}