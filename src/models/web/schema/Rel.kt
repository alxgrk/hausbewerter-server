package de.alxgrk.models.web.schema

enum class Rel(val value: String) {

    SELF("self"),
    CREATE("create"),
    ALL("all"),
    GET_BY_ID("get-by-id"),
    NEXT("next"),
    PREV("prev");

    override fun toString() = value

}