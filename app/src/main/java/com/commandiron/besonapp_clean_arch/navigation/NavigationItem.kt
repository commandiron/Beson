package com.commandiron.besonapp_clean_arch.navigation

import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.EDIT_MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.PRICES

sealed class NavigationItem(
    var title:String? = null,
    var iconResource: Int? = null,
    var route: String
) {
    object HotSplash : NavigationItem(
        route = "hotSplash"
    )
    object Intro : NavigationItem(
        route = "intro"
    )
    object SignUp : NavigationItem(
        route = "signUp"
    )
    object LogIn : NavigationItem(
        route = "logIn"
    )
    object SignUpSteps1 : NavigationItem(
        route = "signUpSteps1"
    )
    object Profile : NavigationItem(
        title = MY_PROFILE,
        iconResource = R.drawable.ic_profile_icon,
        route = "profile"
    )
    object EditProfile : NavigationItem(
        title = EDIT_MY_PROFILE,
        route = "editProfile"
    )
    object MyPriceUpdates : NavigationItem(
        title = MY_PRICE_UPDATES,
        route = "myPriceUpdates"
    )
    object Prices : NavigationItem(
        title = PRICES,
        iconResource = R.drawable.ic_prices_icon,
        route = "prices"
    )
}