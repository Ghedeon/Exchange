package com.exchange.data

import com.exchange.domain.ExchangeRepository
import com.exchange.domain.model.Rate
import com.exchange.domain.model.Rates
import java.util.Currency
import javax.inject.Inject

/*
Dependency inversion in order to keep "data" and "domain" decoupled.
The practicality of this approach is left for the team to discuss.
 */
class ExchangeRepositoryImpl @Inject constructor(private val api: ExchangeApi) : ExchangeRepository {

    override suspend fun rates(baseCurrency: Currency): Result<Rates> = safeIOCall {
        val rates = api.getRates(baseCurrency.currencyCode).rates.entries.associate {
            val currency = Currency.getInstance(it.key)
            currency to Rate(currency, it.value)
        }

        Rates(baseCurrency to rates)
    }
}
