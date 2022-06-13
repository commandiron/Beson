package com.commandiron.besonapp_clean_arch.presentation.model

import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.presentation.model.ConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem

data class MainConstructionItem(
    override val id: Int,
    override val title: String,
    override val imageResource: Int,
    val subConstructionItems: List<SubConstructionItem>
): ConstructionItem
val defaultConstructionItems = listOf(
    MainConstructionItem(
        id = 0,
        title = "Kazi İşleri",
        imageResource = R.drawable.construction_item_kazi,
        subConstructionItems = defaultSubConstructionItems.filter { it.id == 0 || it.id == 1 }
    ),
    MainConstructionItem(
        id = 1,
        title = "Kaba Yapi İşleri",
        imageResource = R.drawable.construction_item_kaba_yapi,
        subConstructionItems = defaultSubConstructionItems.subList(2,6)
    ),
    MainConstructionItem(
        id = 2,
        title = "İç İmalatlar",
        imageResource = R.drawable.construction_item_ic_imalatlar,
        subConstructionItems = defaultSubConstructionItems.subList(7,10)
    ),
    MainConstructionItem(
        id = 3,
        title = "Peysaj",
        imageResource = R.drawable.construction_item_peysaj_ve_cevre,
        subConstructionItems = listOf(defaultSubConstructionItems[11])
    )
)

























