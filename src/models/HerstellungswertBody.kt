package models

import java.math.BigDecimal

data class HerstellungswertBody (
    val baupreisindex: BigDecimal,
    val aussenanlage: BigDecimal
)