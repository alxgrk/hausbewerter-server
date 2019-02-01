package routing.questionnaire

import de.alxgrk.models.schema.Definitions.*
import de.alxgrk.models.schema.DocumentationRef
import de.alxgrk.routing.Routes.*
import de.alxgrk.routing.next
import de.alxgrk.routing.prev
import de.alxgrk.routing.route
import de.alxgrk.routing.self
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema

fun Routing.herstellungswert() {

    route(HERSTELLUNGSWERT) {

        val id = "123"
        val herstellungswert = 3313787.5

        call.respond(
            mapOf(
                "herstellungswert" to herstellungswert,
                schema {
                    add(self(HERSTELLUNGSWERT, id, DocumentationRef(HERSTELLUNGSWERT_BODY)))
                    add(next(SACHWERT, id, DocumentationRef(SACHWERT_BODY)))
                    add(prev(HAUSAUFBAU, id, DocumentationRef(HAUSAUFBAU_BODY)))
                }
            )
        )

    }

}
