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

fun Routing.root() {

    get(Routes.ROOT.path()) {

        call.respond(
            mapOf(
                schema {
                    listOf(
                        self(Routes.ROOT),
                        create(),
                        getById()
                    )
                }
            )
        )

    }


}