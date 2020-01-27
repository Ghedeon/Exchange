@file:UseSerializers(RateSerializer::class)

package com.exchange.data

import kotlinx.serialization.Decoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class RatesResponse(
    @SerialName("base") val base: String,
    @SerialName("date") val date: String,
    @SerialName("rates") val rates: Map<String, BigDecimal>
)

@Serializer(forClass = BigDecimal::class)
private object RateSerializer : KSerializer<BigDecimal> {
    override fun deserialize(decoder: Decoder): BigDecimal = BigDecimal(decoder.decodeDouble())
}

