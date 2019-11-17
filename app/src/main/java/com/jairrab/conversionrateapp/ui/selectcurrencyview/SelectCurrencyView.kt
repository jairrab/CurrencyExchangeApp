package com.jairrab.conversionrateapp.ui.selectcurrencyview

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jairrab.conversionrateapp.databinding.CurrencySelectionBinding.inflate
import com.jairrab.conversionrateapp.ui.selectcurrencyview.adapter.CurrencyAdapter
import com.jairrab.presentation.ActivityViewModel

class SelectCurrencyView : Fragment() {

    private val activityViewModel by activityViewModels<ActivityViewModel>()
    private var currencyAdapter: CurrencyAdapter? = null
    private var currencies: List<String> = ArrayList()
    private var currencyRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflate(inflater, container, false).run {

            currencyAdapter = CurrencyAdapter {
                activityViewModel.onCurrencySelected(it)
                findNavController().popBackStack()
            }

            recyclerView.run {
                currencyRecyclerView = this
                setHasFixedSize(true)
                val portrait =
                    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                layoutManager = GridLayoutManager(context, if (portrait) 3 else 6)
                adapter = currencyAdapter
            }


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                @SuppressLint("DefaultLocale")
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null) return false

                    currencyAdapter?.submitList(currencies.filter {
                        it.toLowerCase().contains(newText.toLowerCase())
                    })

                    return true
                }
            })

            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.currencies.observe(viewLifecycleOwner, Observer {
            currencies = it
            currencyAdapter?.submitList(currencies)
            activityViewModel.stopLoadingStatus()
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        (currencyRecyclerView?.layoutManager as? GridLayoutManager)?.run {
            outState.putInt(
                "currencyRecyclerViewPosition",
                findFirstCompletelyVisibleItemPosition()
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getInt("currencyRecyclerViewPosition")?.let {
            Handler().post { currencyRecyclerView?.scrollToPosition(it) }
        }
    }
}