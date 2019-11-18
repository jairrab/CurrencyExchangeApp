package com.jairrab.conversionrateapp.ui.mainview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jairrab.conversionrateapp.databinding.ItemCurrencyRateBinding.inflate
import com.jairrab.presentation.MainViewModel
import com.jairrab.presentation.model.CurrencyRate


class CurrencyRatesAdapter(private val viewModel: MainViewModel) :
    ListAdapter<CurrencyRate, ItemViewHolder>(COMPARE) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        binding.viewModel = viewModel

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.updateRow(getItem(position))
    }

    companion object {
        private val COMPARE = object : DiffUtil.ItemCallback<CurrencyRate>() {
            override fun areItemsTheSame(
                oldItem: CurrencyRate,
                newItem: CurrencyRate
            ): Boolean =
                oldItem.currency == newItem.currency

            override fun areContentsTheSame(
                oldItem: CurrencyRate,
                newItem: CurrencyRate
            ): Boolean =
                oldItem == newItem
        }
    }
}