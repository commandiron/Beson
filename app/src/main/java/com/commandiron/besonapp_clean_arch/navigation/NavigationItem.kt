package com.commandiron.besonapp_clean_arch.navigation

import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.EDIT_MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.PRICES

sealed class NavigationItem(
    var title:String? = null,
    var iconResource: Int? = null,
    var route: String,
    var isBottomNavigationVisible: Boolean = false,
    var isTopBarVisible: Boolean = false
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
    object SignUpSteps2 : NavigationItem(
        route = "signUpSteps2"
    )
    object SignUpSteps3 : NavigationItem(
        route = "signUpSteps3"
    )
    object Profile : NavigationItem(
        title = MY_PROFILE,
        iconResource = R.drawable.ic_profile_icon,
        route = "profile",
        isBottomNavigationVisible = true,
        isTopBarVisible = true
    )
    object EditProfile : NavigationItem(
        title = EDIT_MY_PROFILE,
        route = "editProfile",
        isTopBarVisible = true
    )
    object MyPriceUpdates : NavigationItem(
        title = MY_PRICE_UPDATES,
        route = "myPriceUpdates",
        isTopBarVisible = true
    )
    object Prices : NavigationItem(
        title = PRICES,
        isBottomNavigationVisible = true,
        iconResource = R.drawable.ic_prices_icon,
        route = "prices"
    )
    companion object {
        val navigationItems = listOf(
            HotSplash,
            Intro,
            SignUp,
            LogIn,
            SignUpSteps1,
            SignUpSteps2,
            SignUpSteps3,
            Profile,
            EditProfile,
            MyPriceUpdates,
            Prices
        )
        fun fromRouteString(route: String?): NavigationItem {
            return when(route){
                HotSplash.route -> HotSplash
                Intro.route -> Intro
                SignUp.route -> SignUp
                LogIn.route -> LogIn
                SignUpSteps1.route -> SignUpSteps1
                SignUpSteps2.route -> SignUpSteps2
                SignUpSteps3.route -> SignUpSteps3
                Profile.route -> Profile
                EditProfile.route -> EditProfile
                MyPriceUpdates.route -> MyPriceUpdates
                Prices.route -> Prices
                else -> HotSplash
            }
        }
    }
}