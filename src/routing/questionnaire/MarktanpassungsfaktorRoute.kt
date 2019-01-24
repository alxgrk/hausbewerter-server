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

fun Routing.marktanpassungsfaktor() {

    put(MARKTANPASSUNGSFAKTOR.path()) {

        val id = "123"
        val vorlaeufigerSachwert = 1213787.5

        call.respond(
            mapOf(
                "vorlaeufigerSachwert" to vorlaeufigerSachwert,
                schema {
                    listOf(
                        self(MARKTANPASSUNGSFAKTOR, id, DocumentationRef(MARKTANPASSUNGSFAKTOR_BODY)),
                        next(GRUNDSTUECKSWERT, id, DocumentationRef(GRUNDSTUECKSWERT_BODY)),
                        prev(BESONDERES, id, DocumentationRef(BESONDERES_BODY))
                    )
                }
            )
        )

    }

}
