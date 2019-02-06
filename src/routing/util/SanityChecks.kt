package de.alxgrk.routing.util

import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.routing.error.NotFoundException
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext
import org.jetbrains.exposed.sql.transactions.transaction

fun PipelineContext<*, ApplicationCall>.idIfExists(repo: QuestionnaireRepository): Int {

    val idInQuestion = call.parameters["id"]

    if (idInQuestion != null) {
        val intId = idInQuestion.toIntOrNull()
        val idExists = transaction {
            repo.exists(
                intId ?: throw NotFoundException("Questionnaires with ID '$idInQuestion' not found.")
            )
        }

        if (!idExists) throw NotFoundException("Questionnaires with ID '$idInQuestion' not found.")

        return intId!!
    }

    throw NotFoundException("Questionnaires without an ID are not allowed.")
}