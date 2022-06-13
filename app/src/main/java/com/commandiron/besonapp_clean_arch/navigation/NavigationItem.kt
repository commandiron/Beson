package com.commandiron.besonapp_clean_arch.navigation

import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.DETAILS
import com.commandiron.besonapp_clean_arch.core.Strings.EDIT_MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PRICE_UPDATES
import com.commandiron.besonapp_clean_arch.core.Strings.MY_PROFILE
import com.commandiron.besonapp_clean_arch.core.Strings.POST_PRICES
import com.commandiron.besonapp_clean_arch.core.Strings.PRICES

sealed class NavigationItem(
    var title:String? = null,
    var iconResource: Int? = null,
    var route: String,
    var withArgs: String,
    var isBottomNavigationVisible: Boolean = false,
    var isTopBarVisible: Boolean = false
) {
    object HotSplash : NavigationItem(
        route = "hotSplash",
        withArgs = "hotSplash"
    )
    object Intro : NavigationItem(
        route = "intro",
        withArgs = "intro"
    )
    object SignUp : NavigationItem(
        route = "signUp",
        withArgs = "signUp"
    )
    object SignIn : NavigationItem(
        route = "signIn",
        withArgs = "signIn"
    )
    object CustomerOrCompany: NavigationItem(
        route = "customerOrCompany",
        withArgs = "customerOrCompany"
    )
    object SignUpStepsAsCustomer1 : NavigationItem(
        route = "signUpStepsAsCustomer1",
        withArgs = "signUpStepsAsCustomer1"
    )
    object SignUpStepsAsCustomer2 : NavigationItem(
        route = "signUpStepsAsCustomer2",
        withArgs = "signUpStepsAsCustomer2"
    )
    object SignUpStepsAsCustomer3 : NavigationItem(
        route = "signUpStepsAsCustomer3",
        withArgs = "signUpStepsAsCustomer3"
    )
    object SignUpStepsAsCompany1 : NavigationItem(
        route = "signUpStepsAsCompany1",
        withArgs = "signUpStepsAsCompany1"
    )
    object SignUpStepsAsCompany2 : NavigationItem(
        route = "signUpStepsAsCompany2",
        withArgs = "signUpStepsAsCompany2"
    )
    object SignUpStepsAsCompany3 : NavigationItem(
        route = "signUpStepsAsCompany3",
        withArgs = "signUpStepsAsCompany3"
    )
    object SignUpStepsAsCompany4 : NavigationItem(
        route = "signUpStepsAsCompany4",
        withArgs = "signUpStepsAsCompany4"
    )
    object SignUpStepsAsCompany5 : NavigationItem(
        route = "signUpStepsAsCompany5",
        withArgs = "signUpStepsAsCompany5"
    )
    object Profile : NavigationItem(
        title = MY_PROFILE,
        iconResource = R.drawable.ic_profile_icon,
        route = "profile",
        withArgs = "profile",
        isBottomNavigationVisible = true,
        isTopBarVisible = true
    )
    object EditProfile : NavigationItem(
        title = EDIT_MY_PROFILE,
        route = "editProfile",
        withArgs = "editProfile",
        isTopBarVisible = true
    )
    object MyPriceUpdates : NavigationItem(
        title = MY_PRICE_UPDATES,
        route = "myPriceUpdates",
        withArgs = "myPriceUpdates",
        isTopBarVisible = true
    )
    object Prices : NavigationItem(
        title = PRICES,
        isBottomNavigationVisible = true,
        isTopBarVisible = true,
        iconResource = R.drawable.ic_prices_icon,
        route = "prices",
        withArgs = "prices"
    )
    object Details : NavigationItem(
        title = DETAILS,
        isTopBarVisible = true,
        route = "details",
        withArgs = "details?userUid={userUid}"
    )
    object PostPrice: NavigationItem(
        title = POST_PRICES,
        route = "postPrice",
        withArgs = "postPrice",
        isTopBarVisible = true,
    )

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append( "?userUid=$arg")
            }
        }
    }

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
            Details,
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
                Details.withArgs -> Details
                PostPrice.route -> PostPrice
                else -> HotSplash
            }
        }
    }
}