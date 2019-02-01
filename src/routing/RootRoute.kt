package de.alxgrk.routing

import de.alxgrk.routing.Routes.ROOT
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema

fun Routing.root() {

    route(ROOT) {

        call.respond(
            mapOf(
                schema {
                    add(self(ROOT))
                    add(create())
                    add(getById())
                }
            )
        )

    }


}