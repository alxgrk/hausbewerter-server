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

fun Routing.sachwert(repo: QuestionnaireRepository) {

    route(SACHWERT) {

        // FIXME somethings wrong with the payload

        val (baujahr, sanierungen) = call.receive<SachwertBody>()

        val id = idIfExists(repo)

        val formerQuestionnaire = transaction { repo.getOne(id) }
        val formerSachwertBody = formerQuestionnaire.toDto<SachwertBody>()
        val formerBesonderesBody = formerQuestionnaire.toDto<BesonderesBody>()
        val formerHerstellungswertBody = formerQuestionnaire.toDto<HerstellungswertBody>()

        val (errechnetesBaujahr, restnutzungsdauer, vorlaeufigerSachwert) = transaction {
            repo.calculateSachwert(id, baujahr, sanierungen.toDbEntity())
        }

        call.respond(
            mapOf(
                "errechnetesBaujahr" to errechnetesBaujahr,
                "restnutzungsdauer" to restnutzungsdauer,
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    add(self(SACHWERT, id.toString(),
                        DocumentationRef(SACHWERT_BODY), formerSachwertBody))
                    add(next(BESONDERES, id.toString(),
                        DocumentationRef(BESONDERES_BODY), formerBesonderesBody))
                    add(prev(HERSTELLUNGSWERT, id.toString(),
                        DocumentationRef(HERSTELLUNGSWERT_BODY), formerHerstellungswertBody))
                }
            )
        )

    }

}
