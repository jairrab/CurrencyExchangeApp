package com.jairrab.conversionrateapp.ui.utils

object FlagMap {

    val emoji: Map<String, String> by lazy {
        object : HashMap<String, String>() {
            init {
                put("AED", "\uD83C\uDDE6\uD83C\uDDEA")
                put("AFN", "\uD83C\uDDE6\uD83C\uDDEB")
                put("ALL", "\uD83C\uDDE6\uD83C\uDDF1")
                put("AMD", "\uD83C\uDDE6\uD83C\uDDF2")
                put("ANG", "\uD83C\uDDF3\uD83C\uDDF1")
                put("ARS", "\uD83C\uDDE6\uD83C\uDDF7")
                put("AUS", "\uD83C\uDDE6\uD83C\uDDFA")
                put("AOA", "\uD83C\uDDE6\uD83C\uDDF4")
                put("AUD", "\uD83C\uDDE6\uD83C\uDDFA")
                put("AWG", "\uD83C\uDDE6\uD83C\uDDFC")
                put("AZN", "\uD83C\uDDE6\uD83C\uDDFF")
                put("BAM", "\uD83C\uDDE7\uD83C\uDDEC")
                put("BBD", "\uD83C\uDDE7\uD83C\uDDE7")
                put("BDT", "\uD83C\uDDE7\uD83C\uDDE9")
                put("BGN", "\uD83C\uDDE7\uD83C\uDDEC")
                put("BHD", "\uD83C\uDDE7\uD83C\uDDED")
                put("BIF", "\uD83C\uDDE7\uD83C\uDDEE")
                put("BMD", "\uD83C\uDDE7\uD83C\uDDF2")
                put("BND", "\uD83C\uDDE7\uD83C\uDDF3")
                put("BOB", "\uD83C\uDDE7\uD83C\uDDF4")
                put("BRL", "\uD83C\uDDE7\uD83C\uDDF7")
                put("BSD", "\uD83C\uDDE7\uD83C\uDDF8")
                put("BTC", "\u20BF")
                put("BTN", "\uD83C\uDDE7\uD83C\uDDF9")
                put("BWP", "\uD83C\uDDE7\uD83C\uDDFC")
                put("BYN", "\uD83C\uDDE7\uD83C\uDDFE")
                put("BYR", "\uD83C\uDDE7\uD83C\uDDFE")
                put("BZD", "\uD83C\uDDE7\uD83C\uDDFF")
                put("CAD", "\uD83C\uDDE8\uD83C\uDDE6")
                put("CDF", "\uD83C\uDDE8\uD83C\uDDE9")
                put("CHF", "\uD83C\uDDE8\uD83C\uDDED")
                put("CLF", "\uD83C\uDDE8\uD83C\uDDF1")
                put("CLP", "\uD83C\uDDE8\uD83C\uDDF1")
                put("CNY", "\uD83C\uDDE8\uD83C\uDDF3")
                put("COP", "\uD83C\uDDE8\uD83C\uDDF4")
                put("CRC", "\uD83C\uDDE8\uD83C\uDDF7")
                put("CUC", "\uD83C\uDDE8\uD83C\uDDFA")
                put("CUP", "\uD83C\uDDE8\uD83C\uDDFA")
                put("CVE", "\uD83C\uDDE8\uD83C\uDDFB")
                put("CZK", "\uD83C\uDDE8\uD83C\uDDFF")
                put("DJF", "\uD83C\uDDE9\uD83C\uDDEF")
                put("DKK", "\uD83C\uDDE9\uD83C\uDDF0")
                put("DOP", "\uD83C\uDDE9\uD83C\uDDF2")
                put("DZD", "\uD83C\uDDE9\uD83C\uDDFF")
                put("EGP", "\uD83C\uDDEA\uD83C\uDDEC")
                put("ERN", "\uD83C\uDDEA\uD83C\uDDF7")
                put("ETB", "\uD83C\uDDEA\uD83C\uDDF9")
                put("EUR", "\uD83C\uDDEA\uD83C\uDDFA")
                put("FJD", "\uD83C\uDDEB\uD83C\uDDEF")
                put("FKP", "\uD83C\uDDEB\uD83C\uDDF0")
                put("GBP", "\uD83C\uDDEC\uD83C\uDDE7")
                put("GEL", "\uD83C\uDDEC\uD83C\uDDEA")
                put("GGP", "\uD83C\uDDEC\uD83C\uDDEC")
                put("GHS", "\uD83C\uDDEC\uD83C\uDDED")
                put("GIP", "\uD83C\uDDEC\uD83C\uDDEE")
                put("GMD", "\uD83C\uDDEC\uD83C\uDDF2")
                put("GNF", "\uD83C\uDDEC\uD83C\uDDF3")
                put("GTQ", "\uD83C\uDDEC\uD83C\uDDF9")
                put("GYD", "\uD83C\uDDEC\uD83C\uDDFE")
                put("HKD", "\uD83C\uDDED\uD83C\uDDF0")
                put("HNL", "\uD83C\uDDED\uD83C\uDDF3")
                put("HRK", "\uD83C\uDDED\uD83C\uDDF7")
                put("HTG", "\uD83C\uDDED\uD83C\uDDF9")
                put("HUF", "\uD83C\uDDED\uD83C\uDDFA")
                put("IDR", "\uD83C\uDDEE\uD83C\uDDE9")
                put("ILS", "\uD83C\uDDEE\uD83C\uDDF1")
                put("IMP", "\uD83C\uDDEE\uD83C\uDDF2")
                put("INR", "\uD83C\uDDEE\uD83C\uDDF3")
                put("IQD", "\uD83C\uDDEE\uD83C\uDDF6")
                put("IRR", "\uD83C\uDDEE\uD83C\uDDF7")
                put("ISK", "\uD83C\uDDEE\uD83C\uDDF8")
                put("JEP", "\uD83C\uDDEF\uD83C\uDDEA")
                put("JMD", "\uD83C\uDDEF\uD83C\uDDF2")
                put("JOD", "\uD83C\uDDEF\uD83C\uDDF4")
                put("JPY", "\uD83C\uDDEF\uD83C\uDDF5")
                put("KES", "\uD83C\uDDF0\uD83C\uDDEA")
                put("KGS", "\uD83C\uDDF0\uD83C\uDDEC")
                put("KHR", "\uD83C\uDDF0\uD83C\uDDED")
                put("KMF", "\uD83C\uDDF0\uD83C\uDDF2")
                put("KPW", "\uD83C\uDDF0\uD83C\uDDF5")
                put("KRW", "\uD83C\uDDF0\uD83C\uDDF7")
                put("KWD", "\uD83C\uDDF0\uD83C\uDDFC")
                put("KYD", "\uD83C\uDDF0\uD83C\uDDFE")
                put("KZT", "\uD83C\uDDF0\uD83C\uDDFF")
                put("LAK", "\uD83C\uDDF1\uD83C\uDDE6")
                put("LBP", "\uD83C\uDDF1\uD83C\uDDE7")
                put("LKR", "\uD83C\uDDF1\uD83C\uDDF0")
                put("LRD", "\uD83C\uDDF1\uD83C\uDDF7")
                put("LSL", "\uD83C\uDDF1\uD83C\uDDF8")
                put("LTL", "\uD83C\uDDF1\uD83C\uDDF9")
                put("LVL", "\uD83C\uDDF1\uD83C\uDDFB")
                put("LYD", "\uD83C\uDDF1\uD83C\uDDFE")
                put("MAD", "\uD83C\uDDF2\uD83C\uDDE6")
                put("MDL", "\uD83C\uDDF2\uD83C\uDDE9")
                put("MGA", "\uD83C\uDDF2\uD83C\uDDEC")
                put("MKD", "\uD83C\uDDF2\uD83C\uDDF0")
                put("MMK", "\uD83C\uDDF2\uD83C\uDDF2")
                put("MNT", "\uD83C\uDDF2\uD83C\uDDF3")
                put("MOP", "\uD83C\uDDF2\uD83C\uDDF4")
                put("MRO", "\uD83C\uDDF2\uD83C\uDDF7")
                put("MVR", "\uD83C\uDDF2\uD83C\uDDF7")
                put("MWK", "\uD83C\uDDF2\uD83C\uDDFC")
                put("MUR", "\uD83C\uDDF2\uD83C\uDDFB")
                put("MXN", "\uD83C\uDDF2\uD83C\uDDFD")
                put("MYR", "\uD83C\uDDF2\uD83C\uDDFE")
                put("MZN", "\uD83C\uDDF2\uD83C\uDDFF")
                put("NAD", "\uD83C\uDDF3\uD83C\uDDE6")
                put("NGN", "\uD83C\uDDF3\uD83C\uDDEC")
                put("NIO", "\uD83C\uDDF3\uD83C\uDDEE")
                put("NOK", "\uD83C\uDDF3\uD83C\uDDF4")
                put("NPR", "\uD83C\uDDF3\uD83C\uDDF5")
                put("NZD", "\uD83C\uDDF3\uD83C\uDDFF")
                put("OMR", "\uD83C\uDDF4\uD83C\uDDF2")
                put("PAB", "\uD83C\uDDF5\uD83C\uDDE6")
                put("PEN", "\uD83C\uDDF5\uD83C\uDDEA")
                put("PGK", "\uD83C\uDDF5\uD83C\uDDEC")
                put("PHP", "\uD83C\uDDF5\uD83C\uDDED")
                put("PKR", "\uD83C\uDDF5\uD83C\uDDF0")
                put("PLN", "\uD83C\uDDF5\uD83C\uDDF1")
                put("PYG", "\uD83C\uDDF5\uD83C\uDDFE")
                put("RON", "\uD83C\uDDF7\uD83C\uDDF4")
                put("RSD", "\uD83C\uDDF7\uD83C\uDDF8")
                put("RUB", "\uD83C\uDDF7\uD83C\uDDFA")
                put("QAR", "\uD83C\uDDF6\uD83C\uDDE6")
                put("RWF", "\uD83C\uDDF7\uD83C\uDDFC")
                put("SAR", "\uD83C\uDDF8\uD83C\uDDE6")
                put("SBD", "\uD83C\uDDF8\uD83C\uDDE7")
                put("SCR", "\uD83C\uDDF8\uD83C\uDDE8")
                put("SDG", "\uD83C\uDDF8\uD83C\uDDE9")
                put("SEK", "\uD83C\uDDF8\uD83C\uDDEA")
                put("SGD", "\uD83C\uDDF8\uD83C\uDDEC")
                put("SHP", "\uD83C\uDDF8\uD83C\uDDED")
                put("SLL", "\uD83C\uDDF8\uD83C\uDDF1")
                put("SOS", "\uD83C\uDDF8\uD83C\uDDF4")
                put("SRD", "\uD83C\uDDF8\uD83C\uDDF7")
                put("STD", "\uD83C\uDDF8\uD83C\uDDF9")
                put("THB", "\uD83C\uDDF9\uD83C\uDDED")
                put("TJS", "\uD83C\uDDF9\uD83C\uDDEF")
                put("TMT", "\uD83C\uDDF9\uD83C\uDDF2")
                put("TOP", "\uD83C\uDDF9\uD83C\uDDF3")
                put("TRY", "\uD83C\uDDF9\uD83C\uDDF7")
                put("TTD", "\uD83C\uDDF9\uD83C\uDDF9")
                put("TWD", "\uD83C\uDDF9\uD83C\uDDFC")
                put("TZS", "\uD83C\uDDF9\uD83C\uDDFF")
                put("UAH", "\uD83C\uDDFA\uD83C\uDDE6")
                put("UGX", "\uD83C\uDDFA\uD83C\uDDEC")
                put("USD", "\uD83C\uDDFA\uD83C\uDDF8")
                put("UYU", "\uD83C\uDDFA\uD83C\uDDFE")
                put("UZS", "\uD83C\uDDFA\uD83C\uDDFF")
                put("VEF", "\uD83C\uDDFB\uD83C\uDDEA")
                put("VND", "\uD83C\uDDFB\uD83C\uDDF3")
                put("VUV", "\uD83C\uDDFB\uD83C\uDDFA")
                put("WST", "\uD83C\uDDFC\uD83C\uDDF8")
                put("XAG", "\uD83D\uDDD1")
                put("XCD", "\uD83C\uDDE7\uD83C\uDDF6")
                put("TND", "\uD83C\uDDF9\uD83C\uDDF4")
                put("XPF", "\uD83C\uDDE7\uD83C\uDDFC")
                put("YER", "\uD83C\uDDF5\uD83C\uDDEB")
                put("ZAR", "\uD83C\uDDFF\uD83C\uDDE6")
                put("ZMK", "\uD83C\uDDFF\uD83C\uDDF2")
                put("ZMW", "\uD83C\uDDFF\uD83C\uDDF2")
                put("ZWL", "\uD83C\uDDFF\uD83C\uDDFC")
            }
        }
    }
}