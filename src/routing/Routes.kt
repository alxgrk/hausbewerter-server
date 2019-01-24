package de.alxgrk.routing

import de.alxgrk.models.schema.Rel
import models.LinkObject
import models.schema.Method
import models.schema.Method.*

val ID_KEY = "{id}"

enum class Routes(private val path: String, val method: Method) {

    ROOT("/", GET),

    NEW("/fragebogen", POST),
    SINGLE("/fragebogen/$ID_KEY", GET),

    FLAECHE("/fragebogen/$ID_KEY/flaeche", PUT),
    HAUSAUFBAU("/fragebogen/$ID_KEY/hausaufbau", PUT),
    HERSTELLUNGSWERT("/fragebogen/$ID_KEY/herstellungswert", PUT),
    SACHWERT("/fragebogen/$ID_KEY/sachwert", PUT),
    BESONDERES("/fragebogen/$ID_KEY/besonderes", PUT),
    MARKTANPASSUNGSFAKTOR("/fragebogen/$ID_KEY/marktanpassungsfaktor", PUT),
    GRUNDSTUECKSWERT("/fragebogen/$ID_KEY/grundstueckswert", PUT);

    fun path(id: String = "") = if (id.isNotEmpty()) path.replace(ID_KEY, id) else path

}

fun self(route: Routes, id: String = "", targetSchema: Any? = null) = LinkObject(relType = Rel.SELF, href = route.path(id), method = route.method, targetSchema = targetSchema)
fun create() = LinkObject(relType = Rel.CREATE, href = Routes.NEW.path(), method = Routes.NEW.method)
fun getById() = LinkObject(relType = Rel.GET_BY_ID, href = Routes.SINGLE.path(), method = Routes.SINGLE.method)
fun next(route: Routes, id: String, targetSchema: Any? = null) = LinkObject(relType = Rel.NEXT, href = route.path(id), method = route.method, targetSchema = targetSchema)
fun prev(route: Routes, id: String, targetSchema: Any? = null) = LinkObject(relType = Rel.PREV, href = route.path(id), method = route.method, targetSchema = targetSchema)