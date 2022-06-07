package com.commandiron.besonapp_clean_arch.domain.use_case

class UseCases(
    val saveShouldShowSplashAndIntro: SaveShouldShowSplashAndIntro,
    val loadShouldShowSplashAndIntro: LoadShouldShowSplashAndIntro,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val saveSignUpStepsName: SaveSignUpStepsName,
    val loadSignUpStepsName: LoadSignUpStepsName,
    val saveSignUpStepsPhoneNumber: SaveSignUpStepsPhoneNumber,
    val loadSignUpStepsPhoneNumber: LoadSignUpStepsPhoneNumber,
    val saveSignUpStepsSelectedConsItemId: SaveSignUpStepsSelectedConsItemId,
    val loadSignUpStepsSelectedConsItem: LoadSignUpStepsSelectedConsItem,
    val validatePostPriceString: ValidatePostPriceString,
    val validatePhoneNumber: ValidatePhoneNumber,
    val signUp: SignUp,
    val signIn: SignIn,
    val signInWithCredential: SignInWithCredential,
    val signOut: SignOut,
    val getUserState: GetUserState,
    val updateUserProfile: UpdateUserProfile,
    val getUserProfile: GetUserProfile
)