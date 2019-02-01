package de.alxgrk.routing

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.schema.Definitions.FLAECHE_BODY
import de.alxgrk.models.schema.DocumentationRef
import de.alxgrk.routing.Routes.FLAECHE
import de.alxgrk.routing.Routes.SINGLE
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.single(repo: QuestionnaireRepository) {

    route(SINGLE) {

        val idInQuestion = call.parameters["id"]

        if (idInQuestion != null) {

            val qById = transaction { repo.getOne(idInQuestion.toInt()) }
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
        } else
            call.respond(HttpStatusCode.NotFound, "Couldn't find a questionnaire with id null.")
    }

}
