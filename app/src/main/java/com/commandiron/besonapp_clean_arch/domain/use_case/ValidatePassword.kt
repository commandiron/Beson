package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Strings.LEAST_EIGHT_CHAR_EMAIL_ERROR_MESSAGE

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if(password.length < 6){
            return ValidationResult(
                successful = false,
                errorMessage = LEAST_EIGHT_CHAR_EMAIL_ERROR_MESSAGE
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}