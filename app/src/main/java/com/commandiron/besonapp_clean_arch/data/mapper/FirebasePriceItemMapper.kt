package com.commandiron.besonapp_clean_arch.data.mapper

import com.commandiron.besonapp_clean_arch.data.model.FirebasePriceItem
import com.commandiron.besonapp_clean_arch.presentation.model.*
import com.commandiron.besonapp_clean_arch.presentation.post_price.defaultLocationCity


fun PriceItem.toFirebasePriceItem(): FirebasePriceItem {
    return FirebasePriceItem(
        subConstructionCategoryId = subConstructionCategoryId,
        priceCategoryId = priceCategoryId,
        price = price.toDoubleOrNull(),
        location = location,
        date = date,
        userBy = userByName,
        userByUid = userByUid
    )
}

fun FirebasePriceItem.toPriceItem(): PriceItem{
    return PriceItem(
        subConstructionCategoryId = subConstructionCategoryId ?: 0,
        priceCategoryId = priceCategoryId ?: 0,
        title = defaultPriceItems[priceCategoryId ?: 0].title,
        unit = defaultPriceItems[priceCategoryId ?: 0].unit,
        price = price.toString(),
        location = location ?: defaultLocationCity,
        date = date ?: 0,
        userByName = userBy ?: "",
        userByUid = userByUid ?: ""
    )
}