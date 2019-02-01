package models.web

data class SchemaObject (
    val links: List<LinkObject>
)

fun schema(links: MutableList<LinkObject>.() -> Unit): Pair<String, SchemaObject> {
    val list = mutableListOf<LinkObject>()

    list.apply(links)

    return "_schema" to SchemaObject(list)
}