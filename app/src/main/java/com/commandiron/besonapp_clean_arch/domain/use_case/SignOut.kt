package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository

class SignOut(
    private val repository: AppRepository
) {
    operator fun invoke() {
        repository.signOut()
    }
}