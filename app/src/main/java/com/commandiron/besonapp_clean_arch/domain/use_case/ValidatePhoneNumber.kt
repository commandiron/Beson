package com.commandiron.besonapp_clean_arch.domain.use_case


class ValidatePhoneNumber {
    operator fun invoke(oldValue: String, newValue: String): String {
        if(newValue.isNotEmpty()){
            if(newValue.toLongOrNull() != null){
                return if(newValue == "0" && newValue.length == 1){
                    oldValue
                }else{
                    if(newValue.length <= 10){
                        newValue
                    }else {
                        oldValue
                    }
                }
            }
        }else{
            return newValue
        }
        return oldValue
    }
}