package com.commandiron.besonapp_clean_arch.navigation

import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.EDIT_MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.POST_PRICES
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
    object SignIn : NavigationItem(
        route = "signIn"
    )
    object CustomerOrCompany: NavigationItem(
        route = "customerOrCompany"
    )
    object SignUpStepsAsCustomer1 : NavigationItem(
        route = "signUpStepsAsCustomer1"
    )
    object SignUpStepsAsCustomer2 : NavigationItem(
        route = "signUpStepsAsCustomer2"
    )
    object SignUpStepsAsCustomer3 : NavigationItem(
        route = "signUpStepsAsCustomer3"
    )
    object SignUpStepsAsCompany1 : NavigationItem(
        route = "signUpStepsAsCompany1"
    )
    object SignUpStepsAsCompany2 : NavigationItem(
        route = "signUpStepsAsCompany2"
    )
    object SignUpStepsAsCompany3 : NavigationItem(
        route = "signUpStepsAsCompany3"
    )
    object SignUpStepsAsCompany4 : NavigationItem(
        route = "signUpStepsAsCompany4"
    )
    object SignUpStepsAsCompany5 : NavigationItem(
        route = "signUpStepsAsCompany5"
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
        isTopBarVisible = true,
        iconResource = R.drawable.ic_prices_icon,
        route = "prices"
    )
    object PostPrice: NavigationItem(
        title = POST_PRICES,
        route = "postPrice",
        isTopBarVisible = true,
    )
    companion object {
        val navigationItems = listOf(
            HotSplash,
            Intro,
            SignUp,
            SignIn,
            CustomerOrCompany,
            SignUpStepsAsCustomer1,
            SignUpStepsAsCustomer2,
            SignUpStepsAsCustomer3,
            SignUpStepsAsCompany1,
            SignUpStepsAsCompany2,
            SignUpStepsAsCompany3,
            SignUpStepsAsCompany4,
            SignUpStepsAsCompany5,
            Profile,
            EditProfile,
            MyPriceUpdates,
            Prices,
            PostPrice
        )
        fun fromRouteString(route: String?): NavigationItem {
            return when(route){
                HotSplash.route -> HotSplash
                Intro.route -> Intro
                SignUp.route -> SignUp
                SignIn.route -> SignIn
                CustomerOrCompany.route -> CustomerOrCompany
                SignUpStepsAsCustomer1.route -> SignUpStepsAsCustomer1
                SignUpStepsAsCustomer2.route -> SignUpStepsAsCustomer2
                SignUpStepsAsCustomer3.route -> SignUpStepsAsCustomer3
                SignUpStepsAsCompany1.route -> SignUpStepsAsCompany1
                SignUpStepsAsCompany2.route -> SignUpStepsAsCompany2
                SignUpStepsAsCompany3.route -> SignUpStepsAsCompany3
                SignUpStepsAsCompany4.route -> SignUpStepsAsCompany4
                SignUpStepsAsCompany5.route -> SignUpStepsAsCompany5
                Profile.route -> Profile
                EditProfile.route -> EditProfile
                MyPriceUpdates.route -> MyPriceUpdates
                Prices.route -> Prices
                PostPrice.route -> PostPrice
                else -> HotSplash
            }
        }
    }
}