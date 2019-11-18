package com.jairrab.conversionrateapp.ui.mainview

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jairrab.conversionrateapp.R
import com.jairrab.conversionrateapp.databinding.MainViewBinding
import com.jairrab.conversionrateapp.databinding.MainViewBinding.inflate
import com.jairrab.conversionrateapp.ui.base.BaseFragment
import com.jairrab.conversionrateapp.ui.mainview.adapter.CurrencyRatesAdapter
import com.jairrab.conversionrateapp.ui.mapper.Mapper
import com.jairrab.conversionrateapp.ui.utils.showView
import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.presentation.ActivityViewModel
import com.jairrab.presentation.MainViewModel
import com.jairrab.presentation.event.EventObserver
import com.jairrab.presentation.state.ChipViewState.*
import com.jairrab.presentation.state.CurrencyViewState.OnCurrencySelection
import com.jairrab.presentation.state.CurrencyViewState.UpdateText
import com.jairrab.presentation.state.NetworkApiState.*
import javax.inject.Inject


class MainView : BaseFragment() {

    @Inject lateinit var mainViewModel: MainViewModel
    @Inject lateinit var mapper: Mapper

    private val activityViewModel by activityViewModels<ActivityViewModel>()
    private var ratesRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflate(layoutInflater, container, false).run {
            viewModel = mainViewModel
            setupObservers()
            setupViews()
            root
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        (ratesRecyclerView?.layoutManager as? GridLayoutManager)?.run {
            outState.putInt("ratesRecyclerViewPosition", findFirstCompletelyVisibleItemPosition())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getInt("ratesRecyclerViewPosition")?.let {
            Handler().post { ratesRecyclerView?.scrollToPosition(it) }
        }
    }

    private fun MainViewBinding.setupViews() {

        ratesRecyclerView = recyclerView

        recyclerView.apply {
            setHasFixedSize(true)
            val portrait = resources.configuration.orientation == ORIENTATION_PORTRAIT
            layoutManager = GridLayoutManager(context, if (portrait) 4 else 6)
            adapter = CurrencyRatesAdapter(mainViewModel)
        }

        inputTv.setSelectAllOnFocus(true)

        mainViewModel.initialize()
    }

    private fun MainViewBinding.setupObservers() {

        mainViewModel.networkApiState.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is ExchangeRateReceived -> updateRecyclerView(it.exchangeRate)
                ExchangeRateActionDone  -> activityViewModel.stopLoadingStatus()
                is CurrenciesRetrieved  -> activityViewModel.updateCurrencies(it.currencies)
                NetworkError            -> processError { activityViewModel.showNetworkErrorMessage() }
                is ApiError             -> processError { activityViewModel.showApiKeyError(it.message) }
                is HasOtherError        -> processError { activityViewModel.showError(it.message) }
            }
        })

        mainViewModel.currencyViewObservable.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is UpdateText       -> currencyTv.text = it.currency
                OnCurrencySelection -> findNavController().navigate(R.id.mainView_to_selectionView)
            }
        })

        mainViewModel.amountViewObservable.observe(viewLifecycleOwner, Observer {
            inputTv.setText(it)
        })

        mainViewModel.cancelViewObservable.observe(viewLifecycleOwner, Observer {
            cancelIv.showView(it)
        })

        mainViewModel.chipViewObservable.observe(viewLifecycleOwner, EventObserver { state ->
            when (state) {
                is SetVisibility -> chipVg.showView(state.isVisible)
                is AddChip       -> addChipView(state.currency)
                is AddChips      -> state.currencies.forEach { addChipView(it) }
                is RemoveChip    -> removeChip(state.currency)
            }
        })

        activityViewModel.currencySelection.observe(viewLifecycleOwner, EventObserver {
            mainViewModel.onCurrencySelected(it)
        })
    }

    private fun MainViewBinding.updateRecyclerView(exchangeRate: ExchangeRate?) {
        val currencyItems = mapper.mapToCurrencyItems(exchangeRate)
        (recyclerView.adapter as CurrencyRatesAdapter).submitList(currencyItems)
    }

    private fun MainViewBinding.addChipView(currency: String) {

        val chip = Chip(context).apply {
            text = currency
            setTextColor(Color.WHITE)
            setChipBackgroundColorResource(R.color.color_green_400)
            setOnClickListener { mainViewModel.onCurrencySelected(currency) }
        }

        chipGroup.addView(chip)
    }

    private fun MainViewBinding.removeChip(currency: String) {
        val chipToRemove = chipGroup.children.first {
            (it as Chip).text.toString() == currency
        }

        chipGroup.removeView(chipToRemove)
    }

    private fun MainViewBinding.processError(doError: () -> Unit) {
        displayView.visibility = View.GONE
        chipGroup.visibility = View.GONE
        doError()
    }
}

