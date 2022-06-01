package com.commandiron.besonapp_clean_arch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.commandiron.besonapp_clean_arch.data.preferences.DefaultPreferences
import com.commandiron.besonapp_clean_arch.data.repository.AppRepositoryImpl
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.domain.use_case.*
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
    @Singleton
    fun provideRepository(): AppRepository {
        return AppRepositoryImpl()
    }

    @Provides
    fun provideUseCases(
        repository: AppRepository,
        preferences: Preferences
    ): UseCases {
        return UseCases(
            saveShouldShowSplashAndIntro = SaveShouldShowSplashAndIntro(preferences = preferences),
            loadShouldShowSplashAndIntro = LoadShouldShowSplashAndIntro(preferences = preferences),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRepeatedPassword = ValidateRepeatedPassword(),
            saveTemporalSignUpStepsName = SaveTemporalSignUpStepsName(preferences = preferences),
            loadTemporalSignUpStepsName = LoadTemporalSignUpStepsName(preferences = preferences),
            saveTemporalSignUpStepsPhoneNumber = SaveTemporalSignUpStepsPhoneNumber(preferences = preferences),
            loadTemporalSignUpStepsPhoneNumber = LoadTemporalSignUpStepsPhoneNumber(preferences = preferences),
            saveTemporalSignUpStepsSelectedConsItem = SaveTemporalSignUpStepsSelectedConsItem(preferences = preferences),
            loadTemporalSignUpStepsSelectedConsItem = LoadTemporalSignUpStepsSelectedConsItem(preferences = preferences),
            validatePostPriceString = ValidatePostPriceString(),
            pushPrice = PushPrice(repository = repository)
        )
    }
}