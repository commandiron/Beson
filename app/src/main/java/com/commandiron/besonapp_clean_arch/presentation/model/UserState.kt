package com.commandiron.besonapp_clean_arch.presentation.model

enum class UserState {
    SIGNED_IN,
    SIGNED_IN_NO_SELECTION_CUSTOMER_OR_COMPANY,
    SIGNED_IN_UNFINISHED_PROFILE_CUSTOMER,
    SIGNED_IN_UNFINISHED_PROFILE_COMPANY,
    SIGNED_OUT
}