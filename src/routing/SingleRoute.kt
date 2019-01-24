package de.alxgrk.routing

import de.alxgrk.models.schema.DocumentationRef
import de.alxgrk.models.schema.Definitions.*
import de.alxgrk.models.schema.schemaDefinitionsUrl
import de.alxgrk.routing.Routes.FLAECHE
import de.alxgrk.routing.Routes.SINGLE
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.options
import models.FlaecheBody
import models.schema
import java.math.BigDecimal

fun Routing.single() {

    get(SINGLE.path()) {

        val id = "123"
        val state = FragebogenState.OFFEN

        call.respond(
            mapOf(
                "id" to id,
                "state" to state,
                schema {
                    listOf(
                        self(SINGLE, id),
                        next(FLAECHE, id, DocumentationRef(FLAECHE_BODY))
                    )
                }
            )
        )

    }

}

enum class FragebogenState {
    OFFEN,
    ABGESCHLOSSEN
}