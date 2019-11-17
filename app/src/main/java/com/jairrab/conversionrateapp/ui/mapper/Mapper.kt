package com.jairrab.conversionrateapp.ui.mapper

import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.conversionrateapp.ui.mainview.model.CurrencyItem
import javax.inject.Inject


class Mapper @Inject constructor() {

    fun mapToCurrencyItems(exchangeRate: ExchangeRate?): List<CurrencyItem>? {

        return exchangeRate?.quotes?.map {
            CurrencyItem(it.key, it.value)
        }
    }
}