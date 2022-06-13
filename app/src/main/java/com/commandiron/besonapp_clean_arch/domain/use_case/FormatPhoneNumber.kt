package com.commandiron.besonapp_clean_arch.domain.use_case

import kotlin.text.StringBuilder

class FormatPhoneNumber {
    operator fun invoke(phoneNumber: String): String {
        val result = StringBuilder(phoneNumber)
            .insert(3,  " ")
            .insert(7,  " ")
            .insert(10,  " ")
            .insert(0, "+90 ")
        return result.toString()
    }
}