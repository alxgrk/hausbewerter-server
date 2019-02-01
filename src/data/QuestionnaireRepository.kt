package de.alxgrk.data

import de.alxgrk.models.entity.Questionnaire
import models.table.QuestionnaireState
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

interface QuestionnaireRepository {

    fun getAll(): SizedIterable<Questionnaire>

    fun getOne(id: Int): Questionnaire

    fun create(): Questionnaire
}

class DaoQuestionnaireRepo : QuestionnaireRepository {

    override fun getAll() = Questionnaire.all()

    override fun getOne(id: Int) = Questionnaire[id]

    override fun create() = Questionnaire.new {
        name = UUID.randomUUID().toString()
        state = QuestionnaireState.OPEN
    }

}