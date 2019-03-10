package de.alxgrk.routing

import de.alxgrk.models.web.schema.Rel
import io.ktor.application.ApplicationCall
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelineInterceptor
import models.web.LinkObject
import models.web.schema.Method
import models.web.schema.Method.*

val ID_KEY = "{id}"

enum class Routes(private val path: String, val method: Method) {

    ROOT("/", GET),

    ALL("/fragebogen", GET),
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

fun self(route: Routes, id: String = "", targetSchema: Any? = null) = LinkObject(
    relType = Rel.SELF,
    href = route.path(id),
    method = route.method,
    targetSchema = targetSchema
)
fun create() = LinkObject(relType = Rel.CREATE, href = Routes.NEW.path(), method = Routes.NEW.method)
fun all() = LinkObject(relType = Rel.ALL, href = Routes.ALL.path(), method = Routes.ALL.method)
fun getById() =
    LinkObject(relType = Rel.GET_BY_ID, href = Routes.SINGLE.path(), method = Routes.SINGLE.method)
fun next(route: Routes, id: String, targetSchema: Any? = null) = LinkObject(
    relType = Rel.NEXT,
    href = route.path(id),
    method = route.method,
    targetSchema = targetSchema
)
fun prev(route: Routes, id: String, targetSchema: Any? = null) = LinkObject(
    relType = Rel.PREV,
    href = route.path(id),
    method = route.method,
    targetSchema = targetSchema
)

fun Routing.route(route: Routes, id: String = "", body: PipelineInterceptor<Unit, ApplicationCall>) = when(route.method) {
    GET -> get(route.path(id), body)
    POST -> post(route.path(id), body)
    PUT -> put(route.path(id), body)
    DELETE -> delete(route.path(id), body)
    PATCH -> patch(route.path(id), body)
    OPTIONS -> options(route.path(id), body)
    HEAD -> head(route.path(id), body)
}