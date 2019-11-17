package com.jairrab.presentation

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.domain.usecases.GetExchangeRate
import com.jairrab.presentation.helper.ChipProcessor
import com.jairrab.presentation.helper.ExchangeRateProcessor
import com.jairrab.presentation.mapper.Mapper
import com.jairrab.presentation.state.MainViewState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import org.junit.*
import org.mockito.Captor

class MainViewModelTest {

    private val getExchangeRate = mock<GetExchangeRate>()
    private val preferences = mock<SharedPreferences>()
    private val editor = mock<SharedPreferences.Editor>()

    private lateinit var mainViewModel: MainViewModel

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    val captor = argumentCaptor<DisposableObserver<ExchangeRate>>()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(
            getExchangeRate = getExchangeRate,
            preferences = preferences,
            exchangeRateProcessor = ExchangeRateProcessor(getExchangeRate, Mapper()),
            chipProcessor = ChipProcessor(preferences)
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun loadPreferences() {

        mainViewModel.loadPreferences()

        val exchangeRate = makeExchangeRate()

        verify(getExchangeRate).execute(captor.capture(), any())

        captor.firstValue.onNext(exchangeRate)

        val expected = (mainViewModel.mainViewState.value?.peekContent()
                as? MainViewState.ExchangeRateReceived)?.exchangeRate

        Assert.assertEquals(exchangeRate, expected)
    }

    @Test
    fun onCurrencySelected() {

        whenever(preferences.edit()).doReturn(editor)

        mainViewModel.onCurrencySelected("USD")

        val exchangeRate = makeExchangeRate()

        verify(getExchangeRate).execute(captor.capture(), any())

        captor.firstValue.onNext(exchangeRate)

        val expected = (mainViewModel.mainViewState.value?.peekContent()
                as? MainViewState.ExchangeRateReceived)?.exchangeRate

        Assert.assertEquals(exchangeRate, expected)
    }

    @Test
    fun onCurrencyCellSelected() {

        whenever(preferences.edit()).doReturn(editor)

        mainViewModel.onCurrencyCellSelected("USD", 5.0)

        verify(getExchangeRate).execute(captor.capture(), any())

        val expected = (mainViewModel.amountViewObservable.value)

        Assert.assertEquals("5.00", expected)
    }

    private fun makeExchangeRate(): ExchangeRate {
        return ExchangeRate(
            source = Rndom.string(),
            quotes = makeQuotes(),
            timestamp = Rndom.long()
        )
    }

    private fun makeQuotes(): HashMap<String, Double> {

        val a = HashMap<String, Double>(0)
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()

        return a
    }
}
