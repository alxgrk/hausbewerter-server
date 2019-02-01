package models.web

import java.math.BigDecimal

data class HerstellungswertBody (
    val baupreisindex: BigDecimal,
    val aussenanlage: BigDecimal
)