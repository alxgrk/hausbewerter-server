package models

import io.ktor.routing.Routing

data class SchemaObject (
    val links: List<LinkObject>
)

fun schema(links: () -> List<LinkObject>) = "_schema" to SchemaObject(links())