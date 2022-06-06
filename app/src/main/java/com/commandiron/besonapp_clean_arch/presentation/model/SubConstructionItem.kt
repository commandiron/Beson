package com.commandiron.besonapp_clean_arch.presentation.model

import com.commandiron.besonapp_clean_arch.R

data class SubConstructionItem(
    override val id: Int,
    override val title: String,
    override val imageResource: Int,
    val priceItems: List<PriceItem>
): ConstructionItem {
    companion object {
        val iksaIsleri = SubConstructionItem(
            id = 0,
            title = "İksa İşleri",
            imageResource = R.drawable.construction_item_iksa,
            priceItems = listOf(
                PriceItem(itemId = 0, title = "ShutCrete Yapılması", unit = "m²"),
                PriceItem(itemId = 1, "Fore Kazık Yapılması", unit = "m")
            )
        )
        val hafriyatIsleri = SubConstructionItem(
            id = 1,
            title = "Hafriyat İşleri",
            imageResource = R.drawable.construction_item_hafriyat,
            priceItems = listOf(
                PriceItem(itemId = 2, title = "Kazı Yapılması", unit = "m³")
            )
        )

        // Kaba Yapı İşleri

        val beton = SubConstructionItem(
            id = 2,
            title = "Beton",
            imageResource = R.drawable.construction_item_beton,
            priceItems = listOf(
                PriceItem(itemId = 3, title = "C30 Beton Dökülmesi", unit = "m³")
            )
        )
        val demir = SubConstructionItem(
            id = 3,
            title = "Demir",
            imageResource = R.drawable.construction_item_demir,
            priceItems = listOf(
                PriceItem(itemId = 4, title = "İnşaat Demiri", unit = "ton")
            )
        )
        val kalip = SubConstructionItem(
            id = 4,
            title = "Kalıp",
            imageResource = R.drawable.construction_item_kalip,
            priceItems = listOf(
                PriceItem(itemId = 5, title = "Kalıp Yapılması", unit = "m²")
            )
        )
        val betonDemirKalip = SubConstructionItem(
            id = 5,
            title = "Beton, Demir, Kalıp İşçilik",
            imageResource = R.drawable.construction_item_beton_demir_kalip_islicik,
            priceItems = listOf(
                PriceItem(itemId = 6, title = "Beton, Demir, Kalıp İşçilik", unit = "m²")
            )
        )
        val duvar = SubConstructionItem(
            id = 6,
            title = "Duvar",
            imageResource = R.drawable.construction_item_duvar,
            priceItems = listOf(
                PriceItem(itemId = 7, title = "Duvar Örülmesi", unit = "m²")
            )
        )
        val izolasyon = SubConstructionItem(
            id = 7,
            title = "İzolasyon",
            imageResource = R.drawable.construction_item_izolasyon,
            priceItems = listOf(
                PriceItem(itemId = 8, title = "Temel İzolasyon Yapılması", unit = "m²"),
                PriceItem(itemId = 9, title = "Perde İzolasyon Yapılması", unit = "m²")
            )
        )
        val digerKabaInsaatMalzeme = SubConstructionItem(
            id = 8,
            title = "Kaba İnşaat Malzemeleri",
            imageResource = R.drawable.construction_item_diger_kaba_insaat_malzeme,
            priceItems = listOf(
                PriceItem(itemId = 10, title = "EPS Asmolen Malzemesi", unit = "m³")
            )
        )
        val betonKesmeDelme = SubConstructionItem(
            id = 9,
            title = "Beton Kesme ve Delme (Karot)",
            imageResource = R.drawable.construction_item_karot,
            priceItems = listOf(
                PriceItem(itemId = 11, title = "24-52 mm Çaplı Karot Alınması", unit = "cm")
            )
        )

        // Çatı İşleri

        val kompleCati = SubConstructionItem(
            id = 10,
            title = "Komple Çatı İmalatı",
            imageResource = R.drawable.construction_item_komple_cati_imalati,
            priceItems = listOf(
                PriceItem(itemId = 12, title = "Komple Çatı Yapılması", unit = "m²")
            )
        )

        val baca = SubConstructionItem(
            id = 11,
            title = "Baca",
            imageResource = R.drawable.construction_item_baca,
            priceItems = listOf(
                PriceItem(itemId = 13, title = "Baca Yapılması", unit = "m")
            )
        )

        // Cephe İşleri

        val cepheKaplamaSistemi = SubConstructionItem(
            id = 12,
            title = "Cephe Kaplama",
            imageResource = R.drawable.construction_item_cephe_kaplama,
            priceItems = listOf(
                PriceItem(itemId = 14, title = "Sinterflex Cephe Kaplaması", unit = "m²"),
                PriceItem(itemId = 15, title = "Kompozit Cephe Kaplaması", unit = "m²")
            )
        )
        val cepheIskele = SubConstructionItem(
            id = 13,
            title = "Cephe İskele",
            imageResource = R.drawable.construction_item_iskele,
            priceItems = listOf(
                PriceItem(itemId = 16, title = "Cephe İskelesi Kurulması", unit = "m²/ay")
            )
        )

        // Mekanik Tesisat İşleri

        val sihhiTesisatIsleri = SubConstructionItem(
            id = 14,
            title = "Sıhhi Tesisat İşleri",
            imageResource = R.drawable.construction_item_sihhi_tesisat_isleri,
            priceItems = listOf(
                PriceItem(itemId = 17, title = "100 m2 Daire Sıhhi Tesisat İşleri", unit = "gtr")
            )
        )
        val sogutmaKlimaSistemi = SubConstructionItem(
            id = 15,
            title = "Soğutma ve Klima Sistemi",
            imageResource = R.drawable.construction_item_sogutma_klima_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 18, title = "100 m2 Daire Klima İşleri", unit = "gtr")
            )
        )
        val havalandirmaSistemi = SubConstructionItem(
            id = 16,
            title = "Havalandırma Sistemi",
            imageResource = R.drawable.construction_item_havalandirma_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 19, title = "1000 m2 Bodrum Havalandırma İşleri", unit = "gtr")
            )
        )
        val mekanikMontaj = SubConstructionItem(
            id = 17,
            title = "Montaj",
            imageResource = R.drawable.construction_item_montaj,
            priceItems = listOf(
                PriceItem(itemId = 20, title = "Klozet", unit = "ad"),
                PriceItem(itemId = 21, title = "Lavabo", unit = "ad"),
                PriceItem(itemId = 22, title = "Batarya", unit = "ad")
            )
        )
        val asansor = SubConstructionItem(
            id = 18,
            title = "Asansör",
            imageResource = R.drawable.construction_item_asansor,
            priceItems = listOf(
                PriceItem(itemId = 23, title = "10 Duraklı 5 Kişilik Asansör Yapılması", unit = "gtr")
            )
        )

        //Elektrik Tesisat İşleri

        val gucDagitimSistemi = SubConstructionItem(
            id = 19,
            title = "Güç Dagitim Sistemi",
            imageResource = R.drawable.construction_item_guc_dagitim_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 24, title = "100 m2 Daire Güç Dağıtım Sistemi İşleri", unit = "gtr")
            )
        )
        val aydinlatmaIsiklandirmaSistemi = SubConstructionItem(
            id = 20,
            title = "Aydınlatma Işıklandırma Sistemi",
            imageResource = R.drawable.construction_item_aydinlatma_ve_isiklandirma_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 25, title = "100 m2 Daire Aydınlatma İşleri", unit = "gtr")
            )
        )
        val iletisimSistemi = SubConstructionItem(
            id = 21,
            title = "İletişim Sistemi",
            imageResource = R.drawable.construction_item_iletisim_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 26, title = "20 Daireli Apartman Diafon Sistemi", unit = "gtr")
            )
        )
        val guvenlikSistemi = SubConstructionItem(
            id = 22,
            title = "Güvenlik Sistemi",
            imageResource = R.drawable.construction_item_guvenlik_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 27, title = "20 Daireli Apartman Güvenlik Sistemi", unit = "gtr")
            )
        )
        val yedekGucSistemi = SubConstructionItem(
            id = 23,
            title = "Yedek Güç Sistemi",
            imageResource = R.drawable.construction_item_yedek_guc_sistemi,
            priceItems = listOf(
                PriceItem(itemId = 28, title = "110 KW Jeneratör", unit = "gtr")
            )
        )

        //İç İmalatlar

        val siva = SubConstructionItem(
            id = 24,
            title = "Siva",
            imageResource = R.drawable.construction_item_siva,
            priceItems = listOf(
                PriceItem(itemId = 29, title = "Alçı Sıva Yapılması", unit = "m²")
            )
        )
        val boyaBadana = SubConstructionItem(
            id = 25,
            title = "Boya ve Badana",
            imageResource = R.drawable.construction_item_boya_badana,
            priceItems = listOf(
                PriceItem(itemId = 30, title = "Boya Yapılması", unit = "m²")
            )
        )
        val icMekanIzolasyon = SubConstructionItem(
            id = 26,
            title = "İç Mekan İzolasyon",
            imageResource = R.drawable.construction_item_ic_mekan_izolasyon,
            priceItems = listOf(
                PriceItem(itemId = 31, title = "Sürme İzolasyon Yapılması", unit = "m²")
            )
        )
        val alcipanKartonpiyer = SubConstructionItem(
            id = 27,
            title = "Alcipan ve Kartonpiyer",
            imageResource = R.drawable.construction_item_alcipan_kartonpiyer,
            priceItems = listOf(
                PriceItem(itemId = 32, title = "Alçıpan Yapılması", unit = "m²"),
                PriceItem(itemId = 33, title = "Kartonpiyer Yapılması", unit = "m")
            )
        )
        val sap = SubConstructionItem(
            id = 28,
            title = "Şap",
            imageResource = R.drawable.construction_item_sap,
            priceItems = listOf(
                PriceItem(itemId = 34, title = "7cm Kalınlığında Şap Yapılması", unit = "m²")
            )
        )
        val metalKorkuluk = SubConstructionItem(
            id = 29,
            title = "Metal Korkuluk",
            imageResource = R.drawable.construction_item_metal_korkuluk,
            priceItems = listOf(
                PriceItem(itemId = 35, title = "Alüminyum Korkuluk Yapılması", unit = "m"),
                PriceItem(itemId = 36, title = "Paslanmaz Korkuluk Yapılması", unit = "m")
            )
        )
        val mermer = SubConstructionItem(
            id = 30,
            title = "Mermer",
            imageResource = R.drawable.construction_item_mermer,
            priceItems = listOf(
                PriceItem(itemId = 37, title = "Yerli Mermer", unit = "m²")
            )
        )
        val seramik = SubConstructionItem(
            id = 31,
            title = "Seramik",
            imageResource = R.drawable.construction_item_seramik,
            priceItems = listOf(
                PriceItem(itemId = 38, title = "Yerli Seramik", unit = "m²")
            )
        )
        val parke = SubConstructionItem(
            id = 32,
            title = "Parke",
            imageResource = R.drawable.construction_item_parke,
            priceItems = listOf(
                PriceItem(itemId = 39, title = "Yerli Lamine Parke", unit = "m²")
            )
        )
        val kapiPencereBalkon = SubConstructionItem(
            id = 33,
            title = "Kapı, Pencere ve Balkon",
            imageResource = R.drawable.construction_item_kapi_pencere_balkon,
            priceItems = listOf(
                PriceItem(itemId = 40, title = "Alüminyum Doğrama", unit = "m²"),
                PriceItem(itemId = 41, title = "PVC Doğrama", unit = "m²")
            )
        )
        val mutfak = SubConstructionItem(
            id = 34,
            title = "Mutfak",
            imageResource = R.drawable.construction_item_mutfak,
            priceItems = listOf(
                PriceItem(itemId = 42, title = "Lake Mutfak Dolabı", unit = "m")
            )
        )

        //Peysaj

        val bahce = SubConstructionItem(
            id = 35,
            title = "Bahce İşleri",
            imageResource = R.drawable.construction_item_bahce,
            priceItems = listOf(
                PriceItem(itemId = 43, title = "Çim", unit = "m²")
            )
        )
    }
}
