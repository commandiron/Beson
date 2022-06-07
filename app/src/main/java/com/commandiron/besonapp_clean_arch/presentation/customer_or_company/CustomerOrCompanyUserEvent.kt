package com.commandiron.besonapp_clean_arch.presentation.customer_or_company

sealed class CustomerOrCompanyUserEvent {
    object CustomerClick: CustomerOrCompanyUserEvent()
    object CompanyClick: CustomerOrCompanyUserEvent()
    object AlertDialogDismiss: CustomerOrCompanyUserEvent()
    object AlertDialogConfirm: CustomerOrCompanyUserEvent()
}
