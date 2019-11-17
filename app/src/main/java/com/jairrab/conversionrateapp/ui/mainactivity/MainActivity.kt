package com.jairrab.conversionrateapp.ui.mainactivity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil.setContentView
import com.jairrab.conversionrateapp.R
import com.jairrab.conversionrateapp.databinding.ActivityMainBinding
import com.jairrab.conversionrateapp.ui.base.BaseActivity
import com.jairrab.conversionrateapp.ui.utils.makeStatusBarTransparent
import com.jairrab.conversionrateapp.ui.utils.showView
import com.jairrab.presentation.ActivityViewModel
import com.jairrab.presentation.event.EventObserver
import com.jairrab.presentation.state.ApiKeyState.SetApiKeyVisibility
import com.jairrab.presentation.state.ApiKeyState.ShowApiKeyReset
import com.jairrab.presentation.state.StatusMessageState.HideMessage
import com.jairrab.presentation.state.StatusMessageState.ShowMessage
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject lateinit var activityViewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeStatusBarTransparent()

        setContentView<ActivityMainBinding>(this, R.layout.activity_main).run {
            viewModel = activityViewModel
            setupObservers(this@MainActivity)
        }

        activityViewModel.startApp()
    }

    private fun ActivityMainBinding.setupObservers(activity: MainActivity) {

        activityViewModel.progressViewVisibility.observe(activity, EventObserver {
            circularProgress.showView(it)
        })

        activityViewModel.statusMessageState.observe(activity, EventObserver {
            when (it) {
                HideMessage    -> progressTv.showView(false)
                is ShowMessage -> progressTv.run {
                    showView(true)
                    text = it.message
                }
            }
        })

        activityViewModel.apiKeyState.observe(activity, EventObserver {
            when (it) {
                is SetApiKeyVisibility -> apiKeyTv.showView(it.isVisible)
                ShowApiKeyReset        -> {
                    EditText(activity).let { input ->
                        AlertDialog.Builder(activity)
                            .setView(input, 50, 0, 50, 0)
                            .setTitle("Enter CurrencyLayer API Key")
                            .setPositiveButton("OK") { _, _ ->
                                activityViewModel.updateApiKey(input.text.toString())
                            }
                            .show()
                    }
                }

            }
        })
    }
}
