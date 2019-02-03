package routing.questionnaire

import de.alxgrk.models.web.schema.Definitions.GRUNDSTUECKSWERT_BODY
import de.alxgrk.models.web.schema.Definitions.MARKTANPASSUNGSFAKTOR_BODY
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes.GRUNDSTUECKSWERT
import de.alxgrk.routing.Routes.MARKTANPASSUNGSFAKTOR
import de.alxgrk.routing.prev
import de.alxgrk.routing.route
import de.alxgrk.routing.self
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema

fun Routing.grundstueckswert() {

    route(GRUNDSTUECKSWERT) {

        val id = "123"
        val gesamtwert = 1513787.5

        call.respond(
            mapOf(
                "gesamtwert" to gesamtwert,
                schema {
                    add(self(GRUNDSTUECKSWERT, id, DocumentationRef(GRUNDSTUECKSWERT_BODY)))
                    add(prev(MARKTANPASSUNGSFAKTOR, id, DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY)))
                }
            )
        )

    }

}
