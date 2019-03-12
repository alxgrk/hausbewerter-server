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
import models.web.BesonderesBody
import models.web.MarktanpassungsfaktorBody
import models.web.SachwertBody
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.besonderes(repo: QuestionnaireRepository) {

    route(BESONDERES) {

        val (besonderes) = call.receive<BesonderesBody>()

        val id = idIfExists(repo)

        val formerQuestionnaire = transaction { repo.getOne(id) }
        val formerBesonderesBody = formerQuestionnaire.toDto<BesonderesBody>()
        val formerMarktanpassungsfaktorBody = formerQuestionnaire.toDto<MarktanpassungsfaktorBody>()
        val formerSachwertBody = formerQuestionnaire.toDto<SachwertBody>()

        val vorlaeufigerSachwert = transaction {
            repo.calculateBesonderes(id,
                besonderes.sachwerte?.map { b -> b.name to b.wert } ?: emptyList())
        }

        call.respond(
            mapOf(
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    add(self(BESONDERES, id.toString(),
                        DocumentationRef(BESONDERES_BODY), formerBesonderesBody))
                    add(next(MARKTANPASSUNGSFAKTOR, id.toString(),
                        DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY), formerMarktanpassungsfaktorBody))
                    add(prev(SACHWERT, id.toString(),
                        DocumentationRef(SACHWERT_BODY), formerSachwertBody))
                }
            )
        )

    }

}
