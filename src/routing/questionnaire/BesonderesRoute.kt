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

fun Routing.besonderes() {

    route(BESONDERES) {

        val id = "123"
        val vorlaeufigerSachwert = 1413787.5

        call.respond(
            mapOf(
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    add(self(BESONDERES, id, DocumentationRef(BESONDERES_BODY)))
                    add(next(MARKTANPASSUNGSFAKTOR, id, DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY)))
                    add(prev(SACHWERT, id, DocumentationRef(SACHWERT_BODY)))
                }
            )
        )

    }

}
