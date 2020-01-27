package com.exchange.domain

import com.exchange.domain.model.Rates
import java.util.Currency

interface ExchangeRepository : Repository {

    suspend fun rates(baseCurrency: Currency): Result<Rates>
}
