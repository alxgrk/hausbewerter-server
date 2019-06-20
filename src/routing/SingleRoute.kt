package de.alxgrk.routing

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.web.mapping.toDto
import de.alxgrk.models.web.schema.Definitions.FLAECHE_BODY
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes.FLAECHE
import de.alxgrk.routing.Routes.SINGLE
import de.alxgrk.routing.util.idIfExists
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.FlaecheBody
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.single(repo: QuestionnaireRepository) {

    route(SINGLE) {

        val id = idIfExists(repo)
        val qById = transaction { repo.getOne(id) }
        val formerFlaecheBody = qById.toDto<FlaecheBody>()

        call.respond(
            mapOf(
                "id" to id.toString(),
                "name" to qById.name,
                "state" to qById.state,
                "gesamtwert" to qById.gesamtwert,
                schema {
                    add(self(SINGLE, id.toString()))
                    add(next(FLAECHE, id.toString(),
                        DocumentationRef(FLAECHE_BODY), formerFlaecheBody))
                }
            )
        )
    }

}
