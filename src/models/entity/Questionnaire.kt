package de.alxgrk.models.entity

import models.table.BesonderesTable
import models.table.Questionnaires
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Questionnaire(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Questionnaire>(Questionnaires)

    var name by Questionnaires.name
    var state by Questionnaires.state

    var breite by Questionnaires.breite
    var laenge by Questionnaires.laenge
    var ebenen by Questionnaires.ebenen
    var wohnflaeche by Questionnaires.wohnflaeche

    var geschosse by Questionnaires.geschosse
    var dach by Questionnaires.dach
    var art by Questionnaires.art
    var standardstufe by Questionnaires.standardstufe
    var zwischenergebnisHausaufbau by Questionnaires.zwischenergebnisHausaufbau

    var baupreisindex by Questionnaires.baupreisindex
    var aussenanlage by Questionnaires.aussenanlage
    var herstellungswert by Questionnaires.herstellungswert

    var baujahr by Questionnaires.baujahr
    var sanierungDach by Questionnaires.sanierungDach
    var sanierungAussenwaende by Questionnaires.sanierungAussenwaende
    var sanierungFensterUndAussentueren by Questionnaires.sanierungFensterUndAussentueren
    var sanierungSonstigeTechnischeAusstattung by Questionnaires.sanierungSonstigeTechnischeAusstattung
    var sanierungFussboden by Questionnaires.sanierungFussboden
    var sanierungHeizung by Questionnaires.sanierungHeizung
    var calculatedBaujahr by Questionnaires.calculatedBaujahr
    var restnutzungsdauer by Questionnaires.restnutzungsdauer
    var vorlaeufigerSachwert by Questionnaires.vorlaeufigerSachwert

    val besonderes by Besonderes referrersOn BesonderesTable.questionnaire
    var vorlaeufigerSachwertMitBesonderem by Questionnaires.vorlaeufigerSachwertMitBesonderem

    var vorlaeufigerSachwertMitMarktanpassungsfaktor by Questionnaires.vorlaeufigerSachwertMitMarktanpassungsfaktor

    var grundstuecksgroesse by Questionnaires.grundstuecksgroesse
    var grundstueckswert by Questionnaires.grundstueckswert

    var gesamtwert by Questionnaires.gesamtwert
}

class Besonderes(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Besonderes>(BesonderesTable)

    var questionnaire by Questionnaire referencedOn BesonderesTable.questionnaire
    var name by BesonderesTable.name
    var value by BesonderesTable.value
}

data class Sanierungen(
    val dach: Int,
    val aussenwaende: Int,
    val fensterUndAussentueren: Int,
    val sonstigeTechnischeAusstattung: Int,
    val fussboden: Int,
    val heizung: Int
)