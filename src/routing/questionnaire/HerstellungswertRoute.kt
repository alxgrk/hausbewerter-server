package routing.questionnaire

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.web.mapping.toDto
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
import models.web.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.herstellungswert(repo: QuestionnaireRepository) {

    route(HERSTELLUNGSWERT) {

        val (baupreisindex, aussenanlage) = call.receive<HerstellungswertBody>()

        val id = idIfExists(repo)

        val formerQuestionnaire = transaction { repo.getOne(id) }
        val formerHerstellungswertBody = formerQuestionnaire.toDto<HerstellungswertBody>()
        val formerSachwertBody = formerQuestionnaire.toDto<SachwertBody>()
        val formerHausaufbauBody = formerQuestionnaire.toDto<HausaufbauBody>()

        val herstellungswert = transaction { repo.calculateHerstellungswert(id, baupreisindex, aussenanlage) }

        call.respond(
            mapOf(
                "herstellungswert" to herstellungswert,
                schema {
                    add(self(HERSTELLUNGSWERT, id.toString(),
                        DocumentationRef(HERSTELLUNGSWERT_BODY), formerHerstellungswertBody))
                    add(next(SACHWERT, id.toString(),
                        DocumentationRef(SACHWERT_BODY), formerSachwertBody))
                    add(prev(HAUSAUFBAU, id.toString(),
                        DocumentationRef(HAUSAUFBAU_BODY), formerHausaufbauBody))
                }
            )
        )

    }

}
