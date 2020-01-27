package com.exchange.ext

import android.annotation.SuppressLint
import android.content.Context
import java.util.Currency

@Suppress("FunctionName")
fun Currency(currencyCode: String): Currency = Currency.getInstance(currencyCode)

@SuppressLint("DefaultLocale")
fun Currency.getFlag(context: Context) = context.resources.getIdentifier(
    "ic_${currencyCode.toLowerCase()}_flag", "mipmap", context.packageName
)
