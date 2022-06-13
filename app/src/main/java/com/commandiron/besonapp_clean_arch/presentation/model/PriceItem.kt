package com.commandiron.besonapp_clean_arch.presentation.model

data class PriceItem(
    val subConstructionCategoryId: Int,
    val priceCategoryId: Int,
    val title: String,
    val unit: String,
    val price: String = "",
    val location: String = "",
    val date: Long = 0,
    val userByName: String = "",
    val userByUid: String = ""
)
val defaultPriceItems = listOf(
    //İksa İşleri
    PriceItem(subConstructionCategoryId = 0, priceCategoryId = 0, title = "ShutCrete Yapılması", unit = "m²"),
    //Hafriyat İşleri
    PriceItem(subConstructionCategoryId = 1, priceCategoryId = 1, title = "Kazı Yapılması", unit = "m³"),
    //Kalıp İşleri
    PriceItem(subConstructionCategoryId = 2, priceCategoryId = 2, title = "Kalıp Yapılması (Düz Ölçü)", unit = "m²"),
    //Demir İşleri
    PriceItem(subConstructionCategoryId = 3, priceCategoryId = 3, title = "Φ12 mm - Φ32 mm İnşaat Demiri", unit = "ton"),
    //Beton İşleri
    PriceItem(subConstructionCategoryId = 4, priceCategoryId = 4, title = "C30 Beton Dökülmesi", unit = "m³"),
    //Duvar İşleri
    PriceItem(subConstructionCategoryId = 5, priceCategoryId = 5, title = "Duvar Örülmesi", unit = "m²"),
    //Cephe İşleri
    PriceItem(subConstructionCategoryId = 6, priceCategoryId = 6, title = "Kompozit Cephe Kaplaması", unit = "m²"),
    //Elektrik Tesisat İşleri
    PriceItem(subConstructionCategoryId = 7, priceCategoryId = 7, title = "100 m2 Daire Elektrik Tesisat İşleri", unit = "gtr"),
    //Mekanik Tesisat İşleri
    PriceItem(subConstructionCategoryId = 8, priceCategoryId = 8, title = "100 m2 Daire Sıhhi Tesisat İşleri", unit = "gtr"),
    //Sıva İşleri
    PriceItem(subConstructionCategoryId = 9, priceCategoryId = 9, title = "Alçı Sıva Yapılması", unit = "m²"),
    //Boya Badana İşleri
    PriceItem(subConstructionCategoryId = 10, priceCategoryId = 10, title = "Boya Yapılması", unit = "m²"),
    //Bahçe İşleri
    PriceItem(subConstructionCategoryId = 11, priceCategoryId = 11, title = "Kilit Taşı Döşenmesi", unit = "m²"),
)