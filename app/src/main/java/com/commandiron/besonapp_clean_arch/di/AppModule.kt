package com.commandiron.besonapp_clean_arch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.commandiron.besonapp_clean_arch.data.preferences.DefaultPreferences
import com.commandiron.besonapp_clean_arch.data.repository.AppRepositoryImpl
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.domain.use_case.*
import com.google.firebase.auth.FirebaseAuth
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
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideRepository(
        auth: FirebaseAuth
    ): AppRepository {
        return AppRepositoryImpl(auth)
    }

    @Provides
    fun provideUseCases(
        repository: AppRepository,
        preferences: Preferences
    ): UseCases {
        return UseCases(
            saveShouldShowSplashAndIntro = SaveShouldShowSplashAndIntro(preferences),
            loadShouldShowSplashAndIntro = LoadShouldShowSplashAndIntro(preferences),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRepeatedPassword = ValidateRepeatedPassword(),
            saveTemporalSignUpStepsName = SaveTemporalSignUpStepsName(preferences),
            loadTemporalSignUpStepsName = LoadTemporalSignUpStepsName(preferences),
            saveTemporalSignUpStepsPhoneNumber = SaveTemporalSignUpStepsPhoneNumber(preferences),
            loadTemporalSignUpStepsPhoneNumber = LoadTemporalSignUpStepsPhoneNumber(preferences),
            saveTemporalSignUpStepsSelectedConsItem = SaveTemporalSignUpStepsSelectedConsItem(preferences),
            loadTemporalSignUpStepsSelectedConsItem = LoadTemporalSignUpStepsSelectedConsItem(preferences),
            validatePostPriceString = ValidatePostPriceString(),
            getUserAuthState = GetUserAuthState(repository),
            signUp = SignUp(repository),
            signIn = SignIn(repository),
            signOut = SignOut(repository)
        )
    }
}