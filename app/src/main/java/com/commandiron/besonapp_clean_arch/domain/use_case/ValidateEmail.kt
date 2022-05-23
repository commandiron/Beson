package com.commandiron.besonapp_clean_arch.domain.use_case

import android.util.Patterns
import com.commandiron.besonapp_clean_arch.core.Strings.EMPTY_TEXT_FIELD_ERROR_MESSAGE
import com.commandiron.besonapp_clean_arch.core.Strings.WRONG_EMAIL_REGEX_ERROR_MESSAGE

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = EMPTY_TEXT_FIELD_ERROR_MESSAGE
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = WRONG_EMAIL_REGEX_ERROR_MESSAGE
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}