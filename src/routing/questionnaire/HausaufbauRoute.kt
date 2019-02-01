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

fun Routing.hausaufbau() {

    route(HAUSAUFBAU) {

        val id = "123"
        val ergebnis = 3213787.5
        val berechnung = "Quadratmeter x Kostenkennwert €/qm"

        call.respond(
            mapOf(
                "ergebnis" to ergebnis,
                "berechnung" to berechnung,
                schema {
                    add(self(HAUSAUFBAU, id, DocumentationRef(HAUSAUFBAU_BODY)))
                    add(next(HERSTELLUNGSWERT, id, DocumentationRef(HERSTELLUNGSWERT_BODY)))
                    add(prev(FLAECHE, id, DocumentationRef(FLAECHE_BODY)))
                }
            )
        )

    }

}
