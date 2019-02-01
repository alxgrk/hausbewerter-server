package routing.questionnaire

import de.alxgrk.models.schema.Definitions.FLAECHE_BODY
import de.alxgrk.models.schema.Definitions.HAUSAUFBAU_BODY
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

fun Routing.flaeche() {

    route(FLAECHE) {

        val id = "123"
        val wohnflaeche = 4042.5

        call.respond(
            mapOf(
                "wohnflaeche" to wohnflaeche,
                schema {
                    add(self(FLAECHE, id, DocumentationRef(FLAECHE_BODY)))
                    add(next(HAUSAUFBAU, id, DocumentationRef(HAUSAUFBAU_BODY)))
                    add(prev(SINGLE, id))
                }
            )
        )

    }

}
