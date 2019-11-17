package com.jairrab.conversionrateapp.ui.selectcurrencyview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jairrab.conversionrateapp.databinding.ItemCurrencyBinding.inflate


class CurrencyAdapter(
    private val onItemClicked: (String) -> Unit
) :
    ListAdapter<String, ItemViewHolder>(COMPARE) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.updateRow(getItem(position))
    }

    companion object {
        private val COMPARE = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem
        }
    }
}