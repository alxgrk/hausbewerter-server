package de.alxgrk.models.entity

import models.table.Questionnaires
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Questionnaire(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Questionnaire>(Questionnaires)

    var name by Questionnaires.name
    var state by Questionnaires.state
}