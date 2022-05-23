package com.commandiron.besonapp_clean_arch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.commandiron.besonapp_clean_arch.data.preferences.DefaultPreferences
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.domain.use_case.ValidateEmail
import com.commandiron.besonapp_clean_arch.domain.use_case.ValidatePassword
import com.commandiron.besonapp_clean_arch.domain.use_case.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    fun provideUseCases(
    ): UseCases {
        return UseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRepeatedPassword = ValidateRepeatedPassword()
        )
    }
}