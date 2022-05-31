package com.commandiron.besonapp_clean_arch.domain.use_case

class UseCases(
    val saveShouldShowSplashAndIntro: SaveShouldShowSplashAndIntro,
    val loadShouldShowSplashAndIntro: LoadShouldShowSplashAndIntro,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val saveTemporalSignUpStepsName: SaveTemporalSignUpStepsName,
    val loadTemporalSignUpStepsName: LoadTemporalSignUpStepsName,
    val saveTemporalSignUpStepsPhoneNumber: SaveTemporalSignUpStepsPhoneNumber,
    val loadTemporalSignUpStepsPhoneNumber: LoadTemporalSignUpStepsPhoneNumber,
    val saveTemporalSignUpStepsSelectedConsItem: SaveTemporalSignUpStepsSelectedConsItem,
    val loadTemporalSignUpStepsSelectedConsItem: LoadTemporalSignUpStepsSelectedConsItem
)