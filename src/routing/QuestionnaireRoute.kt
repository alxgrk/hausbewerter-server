package de.alxgrk.routing

import de.alxgrk.data.QuestionnaireRepository
import io.ktor.routing.Routing
import routing.questionnaire.*

fun Routing.questionnaire(repo: QuestionnaireRepository) {

    flaeche(repo)
    hausaufbau(repo)
    herstellungswert(repo)
    sachwert(repo)
    besonderes(repo)
    marktanpassungsfaktor(repo)
    grundstueckswert(repo)

}
