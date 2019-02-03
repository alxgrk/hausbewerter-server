package routing.questionnaire

import de.alxgrk.models.web.schema.Definitions.*
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes.*
import de.alxgrk.routing.next
import de.alxgrk.routing.prev
import de.alxgrk.routing.route
import de.alxgrk.routing.self
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema

fun Routing.marktanpassungsfaktor() {

    route(MARKTANPASSUNGSFAKTOR) {

        val id = "123"
        val vorlaeufigerSachwert = 1213787.5

        call.respond(
            mapOf(
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    add(self(MARKTANPASSUNGSFAKTOR, id, DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY)))
                    add(next(GRUNDSTUECKSWERT, id, DocumentationRef(GRUNDSTUECKSWERT_BODY)))
                    add(prev(BESONDERES, id, DocumentationRef(BESONDERES_BODY)))
                }
            )
        )

    }

}
