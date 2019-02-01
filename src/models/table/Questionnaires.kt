package models.table

import org.jetbrains.exposed.dao.IntIdTable

object Questionnaires : IntIdTable() {
    val name = varchar("name", 255)
    val state = enumeration("QuestionnaireState", QuestionnaireState::class)
}

enum class QuestionnaireState {
    OPEN,
    FINISHED
}