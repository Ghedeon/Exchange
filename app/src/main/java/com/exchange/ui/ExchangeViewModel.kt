package com.exchange.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchange.domain.GetRates
import com.exchange.domain.model.Rate
import com.exchange.ext.Currency
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigDecimal
import java.util.Currency
import javax.inject.Inject

class ExchangeViewModel @Inject constructor(private val getRates: GetRates) : ViewModel() {

    private val _state = MutableLiveData<UiState>(UiState.Loading)
    val state: LiveData<UiState> = _state

    init {
        getRates.baseRate = Rate(Currency("EUR"), BigDecimal(100))
        getRates()
            .onEach { currencyMap ->
                _state.postValue(UiState.Currencies(currencyMap))
            }
            .launchIn(viewModelScope)
    }

    fun onBaseRateChanged(rate: Rate) {
        getRates.baseRate = rate
    }

    sealed class UiState {
        object Loading : UiState()
        data class Currencies(val data: Map<Currency, Rate>) : UiState()
        object Error : UiState()
    }
}
