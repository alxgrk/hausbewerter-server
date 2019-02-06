package models.table

import org.jetbrains.exposed.dao.IntIdTable

object Questionnaires : IntIdTable() {
    val name = varchar("name", 255)
    val state = enumeration("questionnaireState", QuestionnaireState::class)

    // wohnflaeche
    val breite = decimal("breite", 19, 5).nullable()
    val laenge = decimal("laenge", 19, 5).nullable()
    val ebenen = integer("ebenen").nullable()
    val wohnflaeche = decimal("wohnflaeche", 19, 5).nullable()

    // art des hauses
    val geschosse = enumeration("geschosse", Geschosse::class).nullable()
    val dach = enumeration("dach", Dach::class).nullable()
    val art = enumeration("art", Art::class).nullable()
    val standardstufe = integer("standardstufe").nullable()
    val zwischenergebnisHausaufbau = decimal("zwischenergebnisHausaufbau", 19, 5).nullable()

    // herstellungswert
    val baupreisindex = decimal("baupreisindex", 19, 5).nullable()
    val aussenanlage = decimal("aussenanlage", 19, 5).nullable()
    val herstellungswert = decimal("herstellungswert", 19, 5).nullable()

    // sachwert
    val baujahr = integer("baujahr").nullable()
    val sanierungDach = integer("sanierungDach").nullable()
    val sanierungAussenwaende = integer("sanierungAussenwaende ").nullable()
    val sanierungFensterUndAussentueren = integer("sanierungFensterUndAussentueren").nullable()
    val sanierungSonstigeTechnischeAusstattung = integer("sanierungSonstigeTechnischeAusstattung").nullable()
    val sanierungFussboden = integer("sanierungFussboden").nullable()
    val sanierungHeizung = integer("sanierungHeizung").nullable()
    val calculatedBaujahr = integer("calculatedBaujahr").nullable()
    val restnutzungsdauer = integer("restnutzungsdauer").nullable()
    val vorlaeufigerSachwert = decimal("vorlaeufigerSachwert", 19, 5).nullable()

    // besonderes
    // referenced in 'BesonderesTable'
    val vorlaeufigerSachwertMitBesonderem = decimal("vorlaeufigerSachwertMitBesonderem", 19, 5).nullable()

    // marktanpassungsfaktor
    val vorlaeufigerSachwertMitMarktanpassungsfaktor =
        decimal("vorlaeufigerSachwertMitMarktanpassungsfaktor", 19, 5).nullable()

    // grundstueckswert
    val grundstuecksgroesse = decimal("grundstuecksgroesse", 19, 5).nullable()
    val grundstueckswert = decimal("grundstueckswert", 19, 5).nullable()

    val gesamtwert = decimal("gesamtwert", 19, 5).nullable()
}

object BesonderesTable : IntIdTable() {

    val questionnaire = reference("questionnaire", Questionnaires)
    val name = varchar("name", 255)
    val value = decimal("value", 19, 5)
}

enum class QuestionnaireState {
    OPEN,
    FINISHED
}

enum class Geschosse {
    KG_EG,
    KG_EG_OG,
    EG,
    EG_OG;
}

enum class Dach {
    AUSGEBAUT,
    NICHT_AUSGEBAUT,
    FLACH;
}

enum class Art {
    EIN,
    DOPPEL,
    REIHE,
    REIHENEND,
    REIHENMITTEL;
}