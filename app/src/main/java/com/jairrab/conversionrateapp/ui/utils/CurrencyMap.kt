package com.jairrab.conversionrateapp.ui.utils

import android.icu.util.Currency
import android.os.Build
import java.util.*

object CurrencyMap {

    fun getSymbol(currency: String): String {

        return symbols[currency] ?: let {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Currency.getInstance(currency).symbol
                } else {
                    java.util.Currency.getInstance(currency).symbol
                }
            } catch (e: Exception) {
                ""
            }
        }
    }

    private val symbols: Map<String, String> by lazy {
        object : HashMap<String, String>() {
            init {
                put("ALL", "Lek")
                put("AED", "DH")
                put("ARS", "$")
                put("AWG", "ƒ")
                put("AUD", "$")
                put("AZN", "\u20BC")
                put("BSD", "$")
                put("BBD", "$")
                put("BYR", "p.")
                put("BZD", "BZ$")
                put("BMD", "$")
                put("BOB", "\$b")
                put("BAM", "KM")
                put("BWP", "P")
                put("BGN", "лв")
                put("BRL", "R$")
                put("BND", "$")
                put("KHR", "៛")
                put("CAD", "$")
                put("KYD", "$")
                put("CLP", "$")
                put("CNY", "¥")
                put("COP", "$")
                put("CRC", "₡")
                put("HRK", "kn")
                put("CUP", "₱")
                put("CZK", "Kč")
                put("DKK", "kr")
                put("DOP", "RD$")
                put("XCD", "$")
                put("EGP", "£")
                put("SVC", "$")
                put("EUR", "€")
                put("FKP", "£")
                put("FJD", "$")
                put("GHS", "¢")
                put("GIP", "£")
                put("GTQ", "Q")
                put("GGP", "£")
                put("GYD", "$")
                put("HNL", "L")
                put("HKD", "$")
                put("HUF", "Ft")
                put("ISK", "kr")
                put("INR", "₹")
                put("IDR", "Rp")
                put("IRR", "﷼")
                put("IMP", "£")
                put("ILS", "₪")
                put("JMD", "J$")
                put("JPY", "¥")
                put("JEP", "£")
                put("KZT", "₸")
                put("KPW", "₩")
                put("KRW", "₩")
                put("KGS", "\u2286")
                put("LAK", "₭")
                put("LBP", "£")
                put("LRD", "$")
                put("MKD", "ден")
                put("MOP", "MOP$")
                put("MYR", "RM")
                put("MUR", "₨")
                put("MXN", "$")
                put("MNT", "₮")
                put("MZN", "MT")
                put("NAD", "$")
                put("NPR", "₨")
                put("ANG", "ƒ")
                put("NZD", "$")
                put("NIO", "C$")
                put("NGN", "₦")
                put("NOK", "kr")
                put("OMR", "﷼")
                put("PKR", "₨")
                put("PAB", "B/.")
                put("PYG", "Gs")
                put("PEN", "S/.")
                put("PHP", "₱")
                put("PLN", "zł")
                put("QAR", "﷼")
                put("RON", "lei")
                put("RUB", "\u20BD")
                put("SHP", "£")
                put("SAR", "﷼")
                put("RSD", "Дин.")
                put("SCR", "₨")
                put("SGD", "$")
                put("SBD", "$")
                put("SOS", "S")
                put("ZAR", "R")
                put("LKR", "₨")
                put("SEK", "kr")
                put("CHF", "CHF")
                put("SRD", "$")
                put("SYP", "£")
                put("TWD", "NT$")
                put("THB", "฿")
                put("TTD", "TT$")
                put("TRY", "\u20BA")
                put("TVD", "$")
                put("UAH", "₴")
                put("GBP", "£")
                put("USD", "$")
                put("UYU", "\$U")
                put("UZS", "лв")
                put("VEF", "Bs")
                put("VND", "₫")
                put("YER", "﷼")
                put("ZWD", "Z$")
                put("BYN", "Br")
                put("BTC", "฿")
            }
        }
    }
}