package de.alxgrk.models.web.schema

import com.fasterxml.jackson.annotation.JsonIgnore

val schemaDefinitionsUrl = "https://api.swaggerhub.com/domains/hausbewerter/hausbewerter-domain/1.0.0#/definitions"

data class DocumentationRef(@JsonIgnore private val suffix: Definitions) {

    val `$ref`: String = "$schemaDefinitionsUrl/$suffix"

}

enum class Definitions(val suffix: String) {

    FLAECHE_BODY("FlaecheBody"),
    HAUSAUFBAU_BODY("HausaufbauBody"),
    HERSTELLUNGSWERT_BODY("HerstellungswertBody"),
    SACHWERT_BODY("SachwertBody"),
    BESONDERES_BODY("BesonderesBody"),
    MARKTANPASSUNGSFAKTOR_BODY("MarktanpassungsfaktorBody"),
    GRUNDSTUECKSWERT_BODY("GrundstueckswertBody");

    override fun toString() = suffix
}