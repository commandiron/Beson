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
    val saveSignUpStepsProfilePictureUrl: SaveSignUpStepsProfilePictureUrl,
    val loadSignUpStepsProfilePictureUrl: LoadSignUpStepsProfilePictureUrl,
    val saveSignUpStepsSelectedConsItemId: SaveSignUpStepsSelectedConsItemId,
    val loadSignUpStepsSelectedConsItem: LoadSignUpStepsSelectedConsItem,
    val validatePostPriceString: ValidatePostPriceString,
    val validatePhoneNumber: ValidatePhoneNumber,
    val signUp: SignUp,
    val signIn: SignIn,
    val signInWithGoogleIdToken: SignInWithGoogleIdToken,
    val signOut: SignOut,
    val getUserStatus: GetUserStatus,
    val updateUserProfile: UpdateUserProfile,
    val getUserProfile: GetUserProfile,
    val uploadProfilePicture: UploadProfilePicture,
    val getUserLastKnownLocation: GetUserLastKnownLocation,
    val getLatLngFromLocation: GetLatLngFromLocation,
    val getCityFromLatLng: GetCityFromLatLng,
    val postPrice: PostPrice,
    val getMyPrices: GetMyPrices,
    val getPrices: GetPrices,
    val filterSearchResults: FilterSearchResults,
    val getUserProfileById: GetUserProfileById,
    val formatPhoneNumber: FormatPhoneNumber,
    val deleteMyPrice: DeleteMyPrice,
    val addToFavorites: AddToFavorites,
    val getUserFavoriteStatus: GetUserFavoriteStatus,
    val removeFromFavorites: RemoveFromFavorites,
    val getAllMyFavorites: GetAllMyFavorites
)