package routing.questionnaire

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.web.schema.Definitions.*
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes.*
import de.alxgrk.routing.next
import de.alxgrk.routing.prev
import de.alxgrk.routing.route
import de.alxgrk.routing.self
import de.alxgrk.routing.util.idIfExists
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.HausaufbauBody
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.hausaufbau(repo: QuestionnaireRepository) {

    route(HAUSAUFBAU) {

        val (geschosse, dach, art, standardstufe) = call.receive<HausaufbauBody>()

        val id = idIfExists(repo)
        val result = transaction { repo.calculateHausaufbau(id, geschosse, dach, art, standardstufe) }

        val berechnung = "Quadratmeter x Kostenkennwert €/qm"

        call.respond(
            mapOf(
                "ergebnis" to result,
                "berechnung" to berechnung,
                schema {
                    add(self(HAUSAUFBAU, id.toString(), DocumentationRef(HAUSAUFBAU_BODY)))
                    add(next(HERSTELLUNGSWERT, id.toString(), DocumentationRef(HERSTELLUNGSWERT_BODY)))
                    add(prev(FLAECHE, id.toString(), DocumentationRef(FLAECHE_BODY)))
                }
            )
        )

    }

}
