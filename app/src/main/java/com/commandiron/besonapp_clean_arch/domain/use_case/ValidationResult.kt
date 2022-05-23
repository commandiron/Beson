package com.commandiron.besonapp_clean_arch.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)