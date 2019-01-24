package routing.questionnaire

import de.alxgrk.models.schema.DocumentationRef
import de.alxgrk.models.schema.Definitions.*
import de.alxgrk.routing.Routes.*
import de.alxgrk.routing.prev
import de.alxgrk.routing.self
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.put
import models.schema

fun Routing.grundstueckswert() {

    put(GRUNDSTUECKSWERT.path()) {

        val id = "123"
        val gesamtwert = 1513787.5

        call.respond(
            mapOf(
                "gesamtwert" to gesamtwert,
                schema {
                    listOf(
                        self(GRUNDSTUECKSWERT, id, DocumentationRef(GRUNDSTUECKSWERT_BODY)),
                        prev(MARKTANPASSUNGSFAKTOR, id, DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY))
                    )
                }
            )
        )

    }

}
