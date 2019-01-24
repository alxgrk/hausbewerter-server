package models

data class SachwertBody (
    val baujahr: Int,
    val sanierungen: Sanierungen
)
data class Sanierungen (
    val dach: Int,
    val aussenwaende: Int,
    val fensterUndAussentueren: Int,
    val sonstigeTechnischeAusstattung: Int,
    val fussboden: Int,
    val heizung: Int
)