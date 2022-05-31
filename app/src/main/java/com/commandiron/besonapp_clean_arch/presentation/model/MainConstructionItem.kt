package com.commandiron.besonapp_clean_arch.presentation.model

import com.commandiron.besonapp_clean_arch.R

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
        subConstructionItems = listOf(
            SubConstructionItem.iksaIsleri,
            SubConstructionItem.hafriyatIsleri
        )
    ),
    MainConstructionItem(
        id = 1,
        title = "Kaba Yapi İşleri",
        imageResource = R.drawable.construction_item_kaba_yapi,
        subConstructionItems = listOf(
            SubConstructionItem.beton,
            SubConstructionItem.demir,
            SubConstructionItem.kalip,
            SubConstructionItem.betonDemirKalip,
            SubConstructionItem.duvar,
            SubConstructionItem.izolasyon,
            SubConstructionItem.digerKabaInsaatMalzeme,
            SubConstructionItem.betonKesmeDelme
        )
    ),
    MainConstructionItem(
        id = 2,
        title = "Çatı İşleri",
        imageResource = R.drawable.construction_item_cati,
        subConstructionItems = listOf(
            SubConstructionItem.kompleCati,
            SubConstructionItem.baca
        )
    ),
    MainConstructionItem(
        id = 3,
        title = "Cephe İşleri",
        imageResource = R.drawable.construction_item_cephe,
        subConstructionItems = listOf(
            SubConstructionItem.cepheKaplamaSistemi,
            SubConstructionItem.cepheIskele
        )
    ),
    MainConstructionItem(
        id = 4,
        title = "Mekanik Tesisat İşleri",
        imageResource = R.drawable.construction_item_mekanik_tesisat,
        subConstructionItems= listOf(
            SubConstructionItem.sihhiTesisatIsleri,
            SubConstructionItem.sogutmaKlimaSistemi,
            SubConstructionItem.havalandirmaSistemi,
            SubConstructionItem.mekanikMontaj,
            SubConstructionItem.asansor
        )
    ),
    MainConstructionItem(
        id = 5,
        title = "Elektrik Tesisat İşleri",
        imageResource = R.drawable.construction_item_elektrik_tesisat,
        subConstructionItems = listOf(
            SubConstructionItem.gucDagitimSistemi,
            SubConstructionItem.aydinlatmaIsiklandirmaSistemi,
            SubConstructionItem.iletisimSistemi,
            SubConstructionItem.guvenlikSistemi,
            SubConstructionItem.yedekGucSistemi
        )
    ),
    MainConstructionItem(
        id = 6,
        title = "İç İmalatlar",
        imageResource = R.drawable.construction_item_ic_imalatlar,
        subConstructionItems = listOf(
            SubConstructionItem.siva,
            SubConstructionItem.boyaBadana,
            SubConstructionItem.icMekanIzolasyon,
            SubConstructionItem.alcipanKartonpiyer,
            SubConstructionItem.sap,
            SubConstructionItem.metalKorkuluk,
            SubConstructionItem.mermer,
            SubConstructionItem.seramik,
            SubConstructionItem.parke,
            SubConstructionItem.kapiPencereBalkon,
            SubConstructionItem.mutfak
        )
    ),
    MainConstructionItem(
        id = 7,
        title = "Peysaj ve Çevre Düzenlemesi İşleri",
        imageResource = R.drawable.construction_item_peysaj_ve_cevre,
        subConstructionItems = listOf(
            SubConstructionItem.bahce
        )
    )
)

























