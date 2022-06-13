package com.commandiron.besonapp_clean_arch.presentation.post_price

import androidx.compose.ui.graphics.Color
import com.commandiron.besonapp_clean_arch.core.Strings.THE_LOCATION_REQUIRES_PERMISSION
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.ui.theme.PleaseSelectBlue
import com.google.android.gms.maps.model.LatLng

data class PostPriceState(

    val userName: String = "",
    val userUid: String = "",
    val subConsItems: List<SubConstructionItem>? = null,
    val selectedSubConsItem: SubConstructionItem? = null,
    val priceItems: List<PriceItem>? = null,

    val selectedPriceCategoryId: Int? = null,
    val selectedPriceTitle: String? = null,
    val selectedPriceUnit: String? = null,
    val price: String = "",
    val location: String = defaultLocationCity,
    var date: Long? = null,

    val subConsCategoryDropDownMenuIsExpanded: Boolean = false,
    val subConsCategoryBorderColor: Color = PleaseSelectBlue,

    val priceCategoryDropDownMenuIsExpanded: Boolean = false,
    val priceCategoryBorderColor: Color = Color.Unspecified,

    val priceTextFieldEnabled: Boolean = false,
    val priceTextFieldBorderColor: Color = Color.Unspecified,

    val showAlertDialog: Boolean = false,
    val placeholderIsVisible: Boolean = false,
    val priceIsSent: Boolean = false,

    val fineLocationPermissionGranted: Boolean = false,
    val fineLocationPermissionRationale: String = THE_LOCATION_REQUIRES_PERMISSION,

    val myLatLng: LatLng = defaultLatLng
)

val defaultLatLng = LatLng(41.015137,28.979530)
const val defaultLocationCity = "İstanbul"
