package com.commandiron.besonapp_clean_arch.presentation.post_price

import androidx.compose.ui.graphics.Color
import com.commandiron.besonapp_clean_arch.core.Strings.THE_LOCATION_REQUIRES_PERMISSION
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen
import com.commandiron.besonapp_clean_arch.ui.theme.PleaseSelectBlue
import com.google.android.gms.maps.model.LatLng

data class PostPriceState(

    val subConsItems: List<SubConstructionItem> = defaultConstructionItems[1].subConstructionItems,
    val priceItems: List<PriceItem>? = null,

    val subConsCategoryDropDownMenuIsExpanded: Boolean = false,
    val priceCategoryDropDownMenuIsExpanded: Boolean = false,

    val selectedSubConsItemTitle: String? = null,
    val subConsItemBorderColor: Color = PleaseSelectBlue,

    val selectedPriceItemTitle: String? = null,
    val priceItemBorderColor: Color = Color.Unspecified,

    val priceTextFieldEnabled: Boolean = false,
    val selectedPriceItemUnit: String? = null,
    val price: String = "",
    val priceBorderColor: Color = Color.Unspecified,

    val showAlertDialog: Boolean = false,
    val placeholderIsVisible: Boolean = false,
    val priceIsSent: Boolean = false,

    val fineLocationPermissionGranted: Boolean = false,
    val fineLocationPermissionRationale: String = THE_LOCATION_REQUIRES_PERMISSION,

    val myLatLng: LatLng = defaultLatLng,
    val myLocationCity: String = defaultLocationCity
)

const val defaultLocationCity = "İstanbul"
val defaultLatLng = LatLng(41.015137,28.979530)
