package com.commandiron.besonapp_clean_arch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.commandiron.besonapp_clean_arch.core.StaticUrls.FIREBASE_DATABASE_URL
import com.commandiron.besonapp_clean_arch.data.preferences.DefaultPreferences
import com.commandiron.besonapp_clean_arch.data.repository.AppRepositoryImpl
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.domain.repository.AppRepository
import com.commandiron.besonapp_clean_arch.domain.use_case.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL)

    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideRepository(
        auth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase,
        firebaseStorage: FirebaseStorage
    ): AppRepository {
        return AppRepositoryImpl(auth, firebaseDatabase, firebaseStorage)
    }

    @Provides
    fun provideUseCases(
        repository: AppRepository,
        preferences: Preferences,
        fusedLocationClient: FusedLocationProviderClient
    ): UseCases {
        return UseCases(
            saveShouldShowSplashAndIntro = SaveShouldShowSplashAndIntro(preferences),
            loadShouldShowSplashAndIntro = LoadShouldShowSplashAndIntro(preferences),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRepeatedPassword = ValidateRepeatedPassword(),
            saveSignUpStepsName = SaveSignUpStepsName(preferences),
            loadSignUpStepsName = LoadSignUpStepsName(preferences),
            saveSignUpStepsPhoneNumber = SaveSignUpStepsPhoneNumber(preferences),
            loadSignUpStepsPhoneNumber = LoadSignUpStepsPhoneNumber(preferences),
            saveSignUpStepsProfilePictureUrl =  SaveSignUpStepsProfilePictureUrl(preferences),
            loadSignUpStepsProfilePictureUrl =  LoadSignUpStepsProfilePictureUrl(preferences),
            saveSignUpStepsSelectedConsItemId = SaveSignUpStepsSelectedConsItemId(preferences),
            loadSignUpStepsSelectedConsItem = LoadSignUpStepsSelectedConsItem(preferences),
            validatePostPriceString = ValidatePostPriceString(),
            validatePhoneNumber = ValidatePhoneNumber(),
            signUp = SignUp(repository),
            signIn = SignIn(repository),
            signInWithGoogleIdToken =  SignInWithGoogleIdToken(repository),
            signOut = SignOut(repository),
            getUserState = GetUserState(repository),
            updateUserProfile = UpdateUserProfile(repository),
            getUserProfile = GetUserProfile(repository),
            uploadProfilePicture = UploadProfilePicture(repository),
            getUserLastKnownPosition = GetUserLastKnownPosition(fusedLocationClient)
        )
    }
}