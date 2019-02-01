package models.web

import java.math.BigDecimal

data class Besonderes (
    val sachwerte: List<BesonderesSachwerte>
)

data class BesonderesSachwerte (
    val name: String,
    val wert: BigDecimal
)

data class BesonderesBody (
    val besonderes: Besonderes
)