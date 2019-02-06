package routing.error

import de.alxgrk.models.web.schema.Definitions
import de.alxgrk.models.web.schema.DocumentationRef
import de.alxgrk.routing.Routes
import de.alxgrk.routing.error.PreviousQuestionnaireStepMissingException
import de.alxgrk.routing.error.UnsupportedStandardstufeException
import de.alxgrk.routing.prev
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import models.web.HausaufbauException
import models.web.schema
import org.jetbrains.exposed.exceptions.EntityNotFoundException
import javax.xml.ws.http.HTTPException

fun StatusPages.Configuration.statusConfig() {

    exception<EntityNotFoundException> { cause ->
        call.respond(
            HttpStatusCode.NotFound,
            mapOf("cause" to "${cause.entity.table.tableName} with ID '${cause.id}' not found.")
        )
    }

    exception<HTTPException> { cause ->
        call.respond(
            HttpStatusCode.fromValue(cause.statusCode),
            mapOf("cause" to cause.message)
        )
    }

    exception<HausaufbauException> { cause ->
        call.respond(
            HttpStatusCode.BadRequest,
            mapOf("cause" to cause.message)
        )
    }

    exception<PreviousQuestionnaireStepMissingException> { cause ->
        when (cause.step) {
            Routes.HAUSAUFBAU -> call.respond(
                HttpStatusCode.PreconditionFailed,
                mapOf(
                    "cause" to cause.message,
                    schema {
                        add(prev(Routes.FLAECHE, cause.id, DocumentationRef(Definitions.FLAECHE_BODY)))
                    })
            )
            // TODO add other routes
            else -> call.respond(HttpStatusCode.BadRequest, mapOf("cause" to cause.message))
        }
    }

    exception<UnsupportedStandardstufeException> { cause ->
        call.respond(
            HttpStatusCode.BadRequest,
            mapOf(
                "cause" to cause.message,
                schema {
                    add(prev(Routes.HAUSAUFBAU, cause.id, DocumentationRef(Definitions.HAUSAUFBAU_BODY)))
                })
        )
    }

}