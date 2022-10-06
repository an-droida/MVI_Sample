package com.androida.currencyexchanger.core.custom.enums

enum class CurrencyRates(
    val currency: String,
    var rate: Double? = 0.0,
    var currencyId: Int,
    var baseCurrency: String = "EUR"
) {
    AED("AED", currencyId = 1),
    AFN("AFN", currencyId = 2),
    ALL("ALL", currencyId = 3),
    AMD("AMD", currencyId = 4),
    ANG("ANG", currencyId = 5),
    AOA("AOA", currencyId = 6),
    ARS("ARS", currencyId = 7),
    AUD("AUD", currencyId = 8),
    AWG("AWG", currencyId = 9),
    AZN("AZN", currencyId = 10),
    BAM("BAM", currencyId = 11),
    BBD("BBD", currencyId = 12),
    BDT("BDT", currencyId = 13),
    BGN("BGN", currencyId = 14),
    BHD("BHD", currencyId = 15),
    BIF("BIF", currencyId = 16),
    BMD("BMD", currencyId = 17),
    BND("BND", currencyId = 18),
    BOB("BOB", currencyId = 19),
    BRL("BRL", currencyId = 20),
    BSD("BSD", currencyId = 21),
    BTC("BTC", currencyId = 22),
    BTN("BTN", currencyId = 23),
    BWP("BWP", currencyId = 24),
    BYN("BYN", currencyId = 25),
    BYR("BYR", currencyId = 26),
    BZD("BZD", currencyId = 27),
    CAD("CAD", currencyId = 28),
    CDF("CDF", currencyId = 29),
    CHF("CHF", currencyId = 30),
    CLF("CLF", currencyId = 31),
    CLP("CLP", currencyId = 32),
    CNY("CNY", currencyId = 33),
    COP("COP", currencyId = 34),
    CRC("CRC", currencyId = 35),
    CUC("CUC", currencyId = 36),
    CUP("CUP", currencyId = 37),
    CVE("CVE", currencyId = 38),
    CZK("CZK", currencyId = 39),
    DJF("DJF", currencyId = 40),
    DKK("DKK", currencyId = 41),
    DOP("DOP", currencyId = 42),
    DZD("DZD", currencyId = 43),
    EGP("EGP", currencyId = 44),
    ERN("ERN", currencyId = 45),
    ETB("ETB", currencyId = 46),
    EUR("EUR", currencyId = 47),
    FJD("FJD", currencyId = 48),
    FKP("FKP", currencyId = 49),
    GBP("GBP", currencyId = 50),
    GEL("GEL", currencyId = 51),
    GGP("GGP", currencyId = 52),
    GHS("GHS", currencyId = 53),
    GIP("GIP", currencyId = 54),
    GMD("GMD", currencyId = 55),
    GNF("GNF", currencyId = 56),
    GTQ("GTQ", currencyId = 57),
    GYD("GYD", currencyId = 58),
    HKD("HKD", currencyId = 59),
    HNL("HNL", currencyId = 60),
    HRK("HRK", currencyId = 61),
    HTG("HTG", currencyId = 62),
    HUF("HUF", currencyId = 63),
    IDR("IDR", currencyId = 64),
    ILS("ILS", currencyId = 65),
    IMP("IMP", currencyId = 66),
    INR("INR", currencyId = 67),
    IQD("IQD", currencyId = 68),
    IRR("IRR", currencyId = 69),
    ISK("ISK", currencyId = 70),
    JEP("JEP", currencyId = 71),
    JMD("JMD", currencyId = 72),
    JOD("JOD", currencyId = 73),
    JPY("JPY", currencyId = 74),
    KES("KES", currencyId = 75),
    KGS("KGS", currencyId = 76),
    KHR("KHR", currencyId = 77),
    KMF("KMF", currencyId = 78),
    KPW("KPW", currencyId = 79),
    KRW("KRW", currencyId = 80),
    KWD("KWD", currencyId = 81),
    KYD("KYD", currencyId = 82),
    KZT("KZT", currencyId = 83),
    LAK("LAK", currencyId = 84),
    LBP("LBP", currencyId = 85),
    LKR("LKR", currencyId = 86),
    LRD("LRD", currencyId = 87),
    LSL("LSL", currencyId = 88),
    LTL("LTL", currencyId = 89),
    LVL("LVL", currencyId = 90),
    LYD("LYD", currencyId = 91),
    MAD("MAD", currencyId = 92),
    MDL("MDL", currencyId = 93),
    MGA("MGA", currencyId = 94),
    MKD("MKD", currencyId = 95),
    MMK("MMK", currencyId = 96),
    MNT("MNT", currencyId = 97),
    MOP("MOP", currencyId = 98),
    MRO("MRO", currencyId = 99),
    MUR("MUR", currencyId = 100),
    MVR("MVR", currencyId = 101),
    MWK("MWK", currencyId = 102),
    MXN("MXN", currencyId = 103),
    MYR("MYR", currencyId = 104),
    MZN("MZN", currencyId = 105),
    NAD("NAD", currencyId = 106),
    NGN("NGN", currencyId = 107),
    NIO("NIO", currencyId = 108),
    NOK("NOK", currencyId = 109),
    NPR("NPR", currencyId = 110),
    NZD("NZD", currencyId = 111),
    OMR("OMR", currencyId = 112),
    PAB("PAB", currencyId = 113),
    PEN("PEN", currencyId = 114),
    PGK("PGK", currencyId = 115),
    PHP("PHP", currencyId = 116),
    PKR("PKR", currencyId = 117),
    PLN("PLN", currencyId = 118),
    PYG("PYG", currencyId = 119),
    QAR("QAR", currencyId = 120),
    RON("RON", currencyId = 121),
    RSD("RSD", currencyId = 122),
    RUB("RUB", currencyId = 123),
    RWF("RWF", currencyId = 124),
    SAR("SAR", currencyId = 125),
    SBD("SBD", currencyId = 126),
    SCR("SCR", currencyId = 127),
    SDG("SDG", currencyId = 128),
    SEK("SEK", currencyId = 129),
    SGD("SGD", currencyId = 130),
    SHP("SHP", currencyId = 131),
    SLL("SLL", currencyId = 132),
    SOS("SOS", currencyId = 133),
    SRD("SRD", currencyId = 134),
    STD("STD", currencyId = 135),
    SVC("SVC", currencyId = 136),
    SYP("SYP", currencyId = 137),
    SZL("SZL", currencyId = 138),
    THB("THB", currencyId = 139),
    TJS("TJS", currencyId = 140),
    TMT("TMT", currencyId = 141),
    TND("TND", currencyId = 142),
    TOP("TOP", currencyId = 143),
    TRY("TRY", currencyId = 144),
    TTD("TTD", currencyId = 145),
    TWD("TWD", currencyId = 146),
    TZS("TZS", currencyId = 147),
    UAH("UAH", currencyId = 148),
    UGX("UGX", currencyId = 149),
    USD("USD", currencyId = 150),
    UYU("UYU", currencyId = 151),
    UZS("UZS", currencyId = 152),
    VND("VND", currencyId = 153),
    VUV("VUV", currencyId = 154),
    WST("WST", currencyId = 155),
    XAF("XAF", currencyId = 156),
    XAG("XAG", currencyId = 157),
    XAU("XAU", currencyId = 158),
    XCD("XCD", currencyId = 159),
    XDR("XDR", currencyId = 160),
    XOF("XOF", currencyId = 161),
    XPF("XPF", currencyId = 162),
    YER("YER", currencyId = 163),
    ZAR("ZAR", currencyId = 164),
    ZMK("ZMK", currencyId = 165),
    ZMW("ZMW", currencyId = 166),
    ZWL("ZWL", currencyId = 167),
}