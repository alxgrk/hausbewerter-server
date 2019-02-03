package de.alxgrk.routing

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.web.schema.Definitions.FLAECHE_BODY
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes.FLAECHE
import de.alxgrk.routing.Routes.SINGLE
import de.alxgrk.routing.error.NotFoundException
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.single(repo: QuestionnaireRepository) {

    route(SINGLE) {

        val idInQuestion = call.parameters["id"]!! // can't be null here

        val qById = transaction {
            repo.getOne(idInQuestion.toIntOrNull() ?: throw NotFoundException("Questionnaires with ID '$idInQuestion' not found."))
        }
        val realId = qById.id.toString()

        call.respond(
            mapOf(
                "id" to realId,
                "name" to qById.name,
                "state" to qById.state,
                schema {
                    add(self(SINGLE, realId))
                    add(next(FLAECHE, realId, DocumentationRef(FLAECHE_BODY)))
                }
            )
        )
    }

}
