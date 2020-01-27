package com.exchange.domain

import com.exchange.domain.model.Rate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Currency
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetRates @Inject constructor(private val repository: ExchangeRepository) {

    lateinit var baseRate: Rate

    operator fun invoke(): Flow<Map<Currency, Rate>> = flow {
        while (true) {
            repository.rates(baseRate.currency)
                .onSuccess { result ->
                    if (baseRate.currency == result.baseCurrency) { //drop the frame if old
                        emit(result.rates)
                    }
                }
            delay(DELAY_MS)
        }
    }
        .map(::computeAmount)
        .flowOn(Dispatchers.IO)

    private suspend fun computeAmount(rates: Map<Currency, Rate>): Map<Currency, Rate> {
        val map = mutableMapOf(baseRate.currency to baseRate)
        return rates.mapValues { Rate(it.key, baseRate.amount * it.value.amount) }.toMap(map)
    }
}

private val DELAY_MS = TimeUnit.SECONDS.toMillis(1)

