package routing.questionnaire

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.web.schema.Definitions.GRUNDSTUECKSWERT_BODY
import de.alxgrk.models.web.schema.Definitions.MARKTANPASSUNGSFAKTOR_BODY
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes.GRUNDSTUECKSWERT
import de.alxgrk.routing.Routes.MARKTANPASSUNGSFAKTOR
import de.alxgrk.routing.prev
import de.alxgrk.routing.route
import de.alxgrk.routing.self
import de.alxgrk.routing.util.idIfExists
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.GrundstueckswertBody
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.grundstueckswert(repo: QuestionnaireRepository) {

    route(GRUNDSTUECKSWERT) {

        val (grundstuecksgroesse) = call.receive<GrundstueckswertBody>()

        val id = idIfExists(repo)
        val gesamtwert = transaction {
            repo.calculateGrundstuecksgroesse(id, grundstuecksgroesse)
        }
        call.respond(
            mapOf(
                "gesamtwert" to gesamtwert,
                schema {
                    add(self(GRUNDSTUECKSWERT, id.toString(), DocumentationRef(GRUNDSTUECKSWERT_BODY)))
                    add(prev(MARKTANPASSUNGSFAKTOR, id.toString(), DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY)))
                }
            )
        )

    }

}
