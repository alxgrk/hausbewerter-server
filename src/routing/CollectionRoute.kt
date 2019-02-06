package de.alxgrk.routing

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.routing.Routes.*
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import models.web.schema
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.collection(repo: QuestionnaireRepository) {

    route(ALL) {
        val response = transaction {
            repo.getAll()
                .map { q ->
                    mapOf(
                        "id" to q.id.toString(),
                        "name" to q.name,
                        "state" to q.state,
                        schema {
                            add(self(SINGLE, q.id.toString()))
                        }
                    )
                }
                .fold(mutableListOf<Map<String, Any>>()) { i, e ->
                    i.apply { add(e) }
                }
                .let {
                    mapOf(
                        "members" to it,
                        schema {
                            add(self(ALL))
                        }
                    )
                }
        }

        call.respond(response)
    }

    route(NEW) {

        val id = transaction {
            repo.create()
        }.id.toString()

        call.respond(
            mapOf(
                "id" to id,
                schema {
                    add(self(SINGLE, id))
                }
            )
        )

    }

}
