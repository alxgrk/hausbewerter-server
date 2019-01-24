package de.alxgrk.routing

import de.alxgrk.models.schema.Rel
import de.alxgrk.routing.Routes.*
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import models.LinkObject
import models.schema
import models.schema.Method

fun Routing.collection() {

    post(NEW.path()) {

        val id = "123"

        call.respond(
            mapOf(
                "id" to id,
                schema {
                    listOf(
                        self(SINGLE, id)
                    )
                }
            )
        )

    }

}
