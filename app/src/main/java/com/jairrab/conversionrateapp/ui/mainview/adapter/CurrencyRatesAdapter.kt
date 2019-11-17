package com.jairrab.conversionrateapp.ui.mainview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jairrab.presentation.MainViewModel
import com.jairrab.conversionrateapp.databinding.ItemCurrencyRateBinding.inflate
import com.jairrab.conversionrateapp.ui.mainview.model.CurrencyItem


class CurrencyRatesAdapter(private val viewModel: MainViewModel) :
    ListAdapter<CurrencyItem, ItemViewHolder>(COMPARE) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        binding.viewModel = viewModel

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.updateRow(getItem(position))
    }

    companion object {
        private val COMPARE = object : DiffUtil.ItemCallback<CurrencyItem>() {
            override fun areItemsTheSame(
                oldItem: CurrencyItem,
                newItem: CurrencyItem
            ): Boolean =
                oldItem.currency == newItem.currency

            override fun areContentsTheSame(
                oldItem: CurrencyItem,
                newItem: CurrencyItem
            ): Boolean =
                oldItem == newItem
        }
    }
}