package com.jairrab.conversionrateapp.ui.selectcurrencyview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jairrab.conversionrateapp.databinding.ItemCurrencyBinding

class ItemViewHolder(
    private val binding: ItemCurrencyBinding,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var currency = ""

    init {
        binding.root.setOnClickListener {
            onItemClicked(currency)
        }
    }

    fun updateRow(currency: String) {
        this.currency = currency
        binding.currency = currency
    }

}
