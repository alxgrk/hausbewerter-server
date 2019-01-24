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

fun Routing.sachwert() {

    put(SACHWERT.path()) {

        val id = "123"
        val errechnetesBaujahr = 1978
        val restnutzungsdauer = 30
        val vorlaeufigerSachwert = 1313787.5

        call.respond(
            mapOf(
                "errechnetesBaujahr" to errechnetesBaujahr,
                "restnutzungsdauer" to restnutzungsdauer,
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    listOf(
                        self(SACHWERT, id, DocumentationRef(SACHWERT_BODY)),
                        next(BESONDERES, id, DocumentationRef(BESONDERES_BODY)),
                        prev(HERSTELLUNGSWERT, id, DocumentationRef(HERSTELLUNGSWERT_BODY))
                    )
                }
            )
        )

    }

}
