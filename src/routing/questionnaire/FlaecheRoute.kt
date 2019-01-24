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

fun Routing.flaeche() {

    put(FLAECHE.path()) {

        val id = "123"
        val wohnflaeche = 4042.5

        call.respond(
            mapOf(
                "wohnflaeche" to wohnflaeche,
                schema {
                    listOf(
                        self(FLAECHE, id, DocumentationRef(FLAECHE_BODY)),
                        next(HAUSAUFBAU, id, DocumentationRef(HAUSAUFBAU_BODY)),
                        prev(SINGLE, id)
                    )
                }
            )
        )

    }

}
