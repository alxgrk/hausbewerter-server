package de.alxgrk.routing

import io.ktor.routing.Routing
import routing.questionnaire.*

fun Routing.questionnaire() {

    flaeche()
    hausaufbau()
    herstellungswert()
    sachwert()
    besonderes()
    marktanpassungsfaktor()
    grundstueckswert()

}
