package com.commandiron.besonapp_clean_arch.presentation.model

import com.commandiron.besonapp_clean_arch.R

data class SubConstructionItem(
    override val id: Int,
    override val title: String,
    override val imageResource: Int,
    val priceCategories: List<PriceItem>
): ConstructionItem

val defaultSubConstructionItems = listOf(
    //Kazı İşleri
    SubConstructionItem(
        id = 0,
        title = "İksa İşleri",
        imageResource = R.drawable.construction_item_iksa,
        priceCategories = listOf(defaultPriceItems[0])
    ),
    SubConstructionItem(
        id = 1,
        title = "Hafriyat İşleri",
        imageResource = R.drawable.construction_item_hafriyat,
        priceCategories = listOf(defaultPriceItems[1])
    ),
    // Kaba Yapı İşleri
    SubConstructionItem(
        id = 2,
        title = "Kalıp İşleri",
        imageResource = R.drawable.construction_item_kalip,
        priceCategories = listOf(defaultPriceItems[2])
    ),
    SubConstructionItem(
        id = 3,
        title = "Demir İşleri",
        imageResource = R.drawable.construction_item_demir,
        priceCategories = listOf(defaultPriceItems[3])
    ),
    SubConstructionItem(
        id = 4,
        title = "Beton İşleri",
        imageResource = R.drawable.construction_item_beton,
        priceCategories = listOf(defaultPriceItems[4])
    ),
    SubConstructionItem(
        id = 5,
        title = "Duvar İşleri",
        imageResource = R.drawable.construction_item_duvar,
        priceCategories = listOf(defaultPriceItems[5])
    ),
    SubConstructionItem(
        id = 6,
        title = "Cephe Kaplama İşleri",
        imageResource = R.drawable.construction_item_cephe_kaplama,
        priceCategories = listOf(defaultPriceItems[6])
    ),
    //İç İmalatlar
    SubConstructionItem(
        id = 7,
        title = "Elektrik Tesisat İşleri",
        imageResource = R.drawable.construction_item_elektrik_tesisat,
        priceCategories = listOf(defaultPriceItems[7])
    ),
    SubConstructionItem(
        id = 8,
        title = "Mekanik Tesisat İşleri",
        imageResource = R.drawable.construction_item_mekanik_tesisat,
        priceCategories = listOf(defaultPriceItems[8])
    ),
    SubConstructionItem(
        id = 9,
        title = "Sıva İşleri",
        imageResource = R.drawable.construction_item_siva,
        priceCategories = listOf(defaultPriceItems[9])
    ),
    SubConstructionItem(
        id = 10,
        title = "Boya Badana İşleri",
        imageResource = R.drawable.construction_item_boya_badana,
        priceCategories = listOf(defaultPriceItems[10])
    ),
    //Peysaj
    SubConstructionItem(
        id = 11,
        title = "Bahce İşleri",
        imageResource = R.drawable.construction_item_bahce,
        priceCategories = listOf(defaultPriceItems[11])
    )
)
