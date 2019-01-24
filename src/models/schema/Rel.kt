package de.alxgrk.models.schema

enum class Rel(val value: String) {

    SELF("self"),
    CREATE("create"),
    GET_BY_ID("get-by-id"),
    NEXT("next"),
    PREV("prev");

    override fun toString() = value

}