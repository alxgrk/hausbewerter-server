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
import models.web.HerstellungswertBody
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.herstellungswert(repo: QuestionnaireRepository) {

    route(HERSTELLUNGSWERT) {

        val (baupreisindex, aussenanlage) = call.receive<HerstellungswertBody>()

        val id = idIfExists(repo)
        val herstellungswert = transaction { repo.calculateHerstellungswert(id, baupreisindex, aussenanlage) }

        call.respond(
            mapOf(
                "herstellungswert" to herstellungswert,
                schema {
                    add(self(HERSTELLUNGSWERT, id.toString(), DocumentationRef(HERSTELLUNGSWERT_BODY)))
                    add(next(SACHWERT, id.toString(), DocumentationRef(SACHWERT_BODY)))
                    add(prev(HAUSAUFBAU, id.toString(), DocumentationRef(HAUSAUFBAU_BODY)))
                }
            )
        )

    }

}
