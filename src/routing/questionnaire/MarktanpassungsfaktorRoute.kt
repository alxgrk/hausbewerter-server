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

fun Routing.marktanpassungsfaktor(repo: QuestionnaireRepository) {

    route(MARKTANPASSUNGSFAKTOR) {

        val (marktanpassungsfaktor) = call.receive<MarktanpassungsfaktorBody>()

        val id = idIfExists(repo)

        val formerQuestionnaire = transaction { repo.getOne(id) }
        val formerMarktanpassungsfaktorBody = formerQuestionnaire.toDto<MarktanpassungsfaktorBody>()
        val formerGrundstueckswertBody = formerQuestionnaire.toDto<GrundstueckswertBody>()
        val formerBesonderesBody = formerQuestionnaire.toDto<BesonderesBody>()

        val vorlaeufigerSachwert = transaction {
            repo.calculateMarktanpassungsfaktor(id, marktanpassungsfaktor)
        }

        call.respond(
            mapOf(
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    add(self(MARKTANPASSUNGSFAKTOR, id.toString(),
                        DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY), formerMarktanpassungsfaktorBody))
                    add(next(GRUNDSTUECKSWERT, id.toString(),
                        DocumentationRef(GRUNDSTUECKSWERT_BODY), formerGrundstueckswertBody))
                    add(prev(BESONDERES, id.toString(),
                        DocumentationRef(BESONDERES_BODY), formerBesonderesBody))
                }
            )
        )

    }

}
