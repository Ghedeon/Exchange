package com.exchange.domain.model

import java.math.BigDecimal
import java.util.Currency

data class Rate(val currency: Currency, val amount: BigDecimal)

inline class Rates(private val data: Pair<Currency, Map<Currency, Rate>>) {
    val baseCurrency: Currency get() = data.first
    val rates: Map<Currency, Rate> get() = data.second
}
