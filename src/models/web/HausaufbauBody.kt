package models.web

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
    }

    enum class Dach(val value: String) {
        AUSGEBAUT("ausgebaut"),
        NICHT_AUSGEBAUT("nicht ausgebaut"),
        FLACH("Flachdach / geneigtes Dach");
    }
    
    enum class Art(val value: String) {
        EIN("Einfamilienhaus"),
        DOPPEL("Doppelhaushälfte"),
        REIHE("Reihenhaus"),
        REIHENEND("Reihenendhaus"),
        REIHENMITTEL("Reihenmittelhaus");
    }
}