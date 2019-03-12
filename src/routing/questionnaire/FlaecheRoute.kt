package routing.questionnaire

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.models.web.mapping.toDto
import de.alxgrk.models.web.schema.Definitions.FLAECHE_BODY
import de.alxgrk.models.web.schema.Definitions.HAUSAUFBAU_BODY
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
import models.web.FlaecheBody
import models.web.HausaufbauBody
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.flaeche(repo: QuestionnaireRepository) {

    route(FLAECHE) {

        val (breite, laenge, ebenen) = call.receive<FlaecheBody>()

        val id = idIfExists(repo)

        val formerQuestionnaire = transaction { repo.getOne(id) }
        val formerFlaecheBody = formerQuestionnaire.toDto<FlaecheBody>()
        val formerHausaufbauBody = formerQuestionnaire.toDto<HausaufbauBody>()

        val wohnflaeche = transaction { repo.calculateFlaeche(id, breite, laenge, ebenen) }

        call.respond(
            mapOf(
                "wohnflaeche" to wohnflaeche,
                schema {
                    add(self(FLAECHE, id.toString(),
                        DocumentationRef(FLAECHE_BODY), formerFlaecheBody))
                    add(next(HAUSAUFBAU, id.toString(),
                        DocumentationRef(HAUSAUFBAU_BODY), formerHausaufbauBody))
                    add(prev(SINGLE, id.toString()))
                }
            )
        )

    }

}
