package models.web.mapping

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import models.web.HausaufbauBody
import models.web.HausaufbauBody.*

class HausaufbauBodyDeserializer : StdDeserializer<HausaufbauBody>(HausaufbauBody::class.java) {

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): HausaufbauBody {

        val node: JsonNode = jp.codec.readTree(jp)

        val geschosse = Geschosse.from(node.get("geschosse").asText())
        val dach = Dach.from(node.get("dach").asText())
        val art = Art.from(node.get("art").asText())
        val standardstufe = node.get("standardstufe").asInt()

        return HausaufbauBody(geschosse, dach, art, standardstufe)
    }
}
