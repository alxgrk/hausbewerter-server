package de.alxgrk.models.calculation

import de.alxgrk.models.entity.Sanierungen
import java.math.BigDecimal

private val INFLUENCE = BigDecimal("0.69")

fun Sanierungen.calculatedBaujahr(baujahr: Int): Int {

    val average = listOf(dach, aussenwaende, fensterUndAussentueren, sonstigeTechnischeAusstattung, fussboden, heizung)
        .average()
        .toInt()

    val imaginaererBaujahrAufschlag = INFLUENCE.multiply((average - baujahr).toBigDecimal())

    val imaginaererBaujahrAufschlagRounded = Math.round(imaginaererBaujahrAufschlag.toDouble())

    return baujahr + imaginaererBaujahrAufschlagRounded.toInt()
}