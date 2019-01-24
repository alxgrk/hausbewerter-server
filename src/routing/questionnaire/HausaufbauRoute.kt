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

fun Routing.hausaufbau() {

    put(HAUSAUFBAU.path()) {

        val id = "123"
        val ergebnis = 3213787.5
        val berechnung = "Quadratmeter x Kostenkennwert €/qm"

        call.respond(
            mapOf(
                "ergebnis" to ergebnis,
                "berechnung" to berechnung,
                schema {
                    listOf(
                        self(HAUSAUFBAU, id, DocumentationRef(HAUSAUFBAU_BODY)),
                        next(HERSTELLUNGSWERT, id, DocumentationRef(HERSTELLUNGSWERT_BODY)),
                        prev(FLAECHE, id, DocumentationRef(FLAECHE_BODY))
                    )
                }
            )
        )

    }

}
