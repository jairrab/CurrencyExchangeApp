package com.jairrab.conversionrateapp.ui.mainview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jairrab.conversionrateapp.databinding.ItemCurrencyRateBinding
import com.jairrab.presentation.model.CurrencyRate

class ItemViewHolder(private val binding: ItemCurrencyRateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun updateRow(item: CurrencyRate) {
        binding.item = item
    }

}
