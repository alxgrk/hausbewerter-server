package models.web

import com.fasterxml.jackson.annotation.JsonIgnore
import de.alxgrk.models.web.schema.Rel
import models.web.schema.Method

data class LinkObject (
    @JsonIgnore private val relType: Rel,
    val rel: String = relType.toString(),
    val href: String,
    val method: Method = Method.GET,
    val targetSchema: Any? = null,
    val formerData: Any? = null
)