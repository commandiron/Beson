package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.core.Strings.NOT_SAME_PASSWORD_ERROR_MESSAGE

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword){
            return ValidationResult(
                successful = false,
                errorMessage = NOT_SAME_PASSWORD_ERROR_MESSAGE
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}