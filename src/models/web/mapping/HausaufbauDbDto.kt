package de.alxgrk.models.web.mapping

import models.table.Art
import models.table.Dach
import models.table.Geschosse
import models.web.HausaufbauBody

// DTO -> DB
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

// DB -> DTO
fun Geschosse.toDto() = when (this) {
    Geschosse.KG_EG -> HausaufbauBody.Geschosse.KG_EG
    Geschosse.KG_EG_OG -> HausaufbauBody.Geschosse.KG_EG_OG
    Geschosse.EG -> HausaufbauBody.Geschosse.EG
    Geschosse.EG_OG -> HausaufbauBody.Geschosse.EG_OG
}

fun Dach.toDto() = when (this) {
    Dach.AUSGEBAUT -> HausaufbauBody.Dach.AUSGEBAUT
    Dach.NICHT_AUSGEBAUT -> HausaufbauBody.Dach.NICHT_AUSGEBAUT
    Dach.FLACH -> HausaufbauBody.Dach.FLACH
}

fun Art.toDto() = when (this) {
    Art.EIN -> HausaufbauBody.Art.EIN
    Art.DOPPEL -> HausaufbauBody.Art.DOPPEL
    Art.REIHE -> HausaufbauBody.Art.REIHE
    Art.REIHENEND -> HausaufbauBody.Art.REIHENEND
    Art.REIHENMITTEL -> HausaufbauBody.Art.REIHENMITTEL
}