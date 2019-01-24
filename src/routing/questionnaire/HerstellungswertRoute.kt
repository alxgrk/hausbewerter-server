package routing.questionnaire

import de.alxgrk.models.schema.DocumentationRef
import de.alxgrk.models.schema.Definitions.*
import de.alxgrk.routing.Routes.*
import de.alxgrk.routing.next
import de.alxgrk.routing.prev
import de.alxgrk.routing.self
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.put
import models.schema

fun Routing.herstellungswert() {

    put(HERSTELLUNGSWERT.path()) {

        val id = "123"
        val herstellungswert = 3313787.5

        call.respond(
            mapOf(
                "herstellungswert" to herstellungswert,
                schema {
                    listOf(
                        self(HERSTELLUNGSWERT, id, DocumentationRef(HERSTELLUNGSWERT_BODY)),
                        next(SACHWERT, id, DocumentationRef(SACHWERT_BODY)),
                        prev(HAUSAUFBAU, id, DocumentationRef(HAUSAUFBAU_BODY))
                    )
                }
            )
        )

    }

}
