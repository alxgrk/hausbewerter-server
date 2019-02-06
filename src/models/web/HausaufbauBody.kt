package models.web

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import models.table.Art
import models.table.Dach
import models.table.Geschosse
import models.web.mapping.HausaufbauBodyDeserializer

@JsonDeserialize(using = HausaufbauBodyDeserializer::class)
data class HausaufbauBody(
    val geschosse: Geschosse,
    val dach: Dach,
    val art: Art,
    val standardstufe: Int

) {

    enum class Geschosse(val value: String) {
        KG_EG("Kellergeschoss + Erdgeschoss"),
        KG_EG_OG("Kellergeschoss + Erdgeschoss + Obergeschoss"),
        EG("Erdgeschoss"),
        EG_OG("Erdgeschoss + Obergeschoss");

        companion object {
            fun from(v: String) = when (v) {
                KG_EG.value -> KG_EG
                KG_EG_OG.value -> KG_EG_OG
                EG.value -> EG
                EG_OG.value -> EG_OG
                else -> throw HausaufbauException("Geschosse-Wert '$v' unbekannt.")
            }
        }
    }

    enum class Dach(val value: String) {
        AUSGEBAUT("ausgebaut"),
        NICHT_AUSGEBAUT("nicht ausgebaut"),
        FLACH("Flachdach / geneigtes Dach");

        companion object {
            fun from(v: String) = when (v) {
                AUSGEBAUT.value -> AUSGEBAUT
                NICHT_AUSGEBAUT.value -> NICHT_AUSGEBAUT
                FLACH.value -> FLACH
                else -> throw HausaufbauException("Dach-Wert '$v' unbekannt.")
            }
        }
    }

    enum class Art(val value: String) {
        EIN("Einfamilienhaus"),
        DOPPEL("Doppelhaushälfte"),
        REIHE("Reihenhaus"),
        REIHENEND("Reihenendhaus"),
        REIHENMITTEL("Reihenmittelhaus");
        
        companion object {
            fun from(v: String) = when (v) {
                EIN.value -> EIN
                DOPPEL.value -> DOPPEL
                REIHE.value -> REIHE
                REIHENEND.value -> REIHENEND
                REIHENMITTEL.value -> REIHENMITTEL
                else -> throw HausaufbauException("Art-Wert '$v' unbekannt.")
            }
        }
    }
}

class HausaufbauException(message: String) : RuntimeException(message)

fun HausaufbauBody.Geschosse.toDbEntity() = when (this) {
    HausaufbauBody.Geschosse.KG_EG -> Geschosse.KG_EG
    HausaufbauBody.Geschosse.KG_EG_OG -> Geschosse.KG_EG_OG
    HausaufbauBody.Geschosse.EG -> Geschosse.EG
    HausaufbauBody.Geschosse.EG_OG -> Geschosse.EG_OG
}

fun HausaufbauBody.Dach.toDbEntity() = when (this) {
    HausaufbauBody.Dach.AUSGEBAUT -> Dach.AUSGEBAUT
    HausaufbauBody.Dach.NICHT_AUSGEBAUT -> Dach.NICHT_AUSGEBAUT
    HausaufbauBody.Dach.FLACH -> Dach.FLACH
}

fun HausaufbauBody.Art.toDbEntity() = when (this) {
    HausaufbauBody.Art.EIN -> Art.EIN
    HausaufbauBody.Art.DOPPEL -> Art.DOPPEL
    HausaufbauBody.Art.REIHE -> Art.REIHE
    HausaufbauBody.Art.REIHENEND -> Art.REIHENEND
    HausaufbauBody.Art.REIHENMITTEL -> Art.REIHENMITTEL
}