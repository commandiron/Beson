package com.commandiron.besonapp_clean_arch.domain.use_case

class ValidatePostPriceString {
    operator fun invoke(oldValue: String, newValue: String): String {
        if(newValue.isNotEmpty()){
            if(newValue.toDoubleOrNull() != null){
                if(newValue.length < 12){
                    if(newValue.contains(".")){
                        val dotIndex = newValue.indexOf(".")
                        val lastIndex = newValue.lastIndex
                        if(lastIndex - dotIndex < 3){
                            return newValue
                        }
                    }else{
                        return newValue
                    }
                }
            }
        }else{
            return newValue
        }
        return oldValue
    }
}