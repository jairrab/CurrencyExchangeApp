package com.jairrab.conversionrateapp.ui.mainview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jairrab.conversionrateapp.databinding.ItemCurrencyRateBinding
import com.jairrab.conversionrateapp.ui.mainview.model.CurrencyItem

class ItemViewHolder(private val binding: ItemCurrencyRateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun updateRow(item: CurrencyItem) {
        binding.item = item
    }

}
