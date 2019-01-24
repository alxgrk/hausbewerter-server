package de.alxgrk

import assertk.Assert
import assertk.assertions.isEqualTo

@Suppress("DEPRECATION")
fun Assert<String?>.isEqualToIgnoringWhitespaces(expected: String) {
    val trimmedActual = actual?.replace("\\s+".toRegex(), "")
    val trimmedExpected = expected.replace("\\s+".toRegex(), "")
    assertThat(trimmedActual).isEqualTo(trimmedExpected)
}