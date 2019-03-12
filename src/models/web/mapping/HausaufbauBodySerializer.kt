package models.web.mapping

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import models.web.HausaufbauBody
import models.web.HausaufbauBody.*

class HausaufbauBodySerializer : StdSerializer<HausaufbauBody>(HausaufbauBody::class.java) {

    override fun serialize(value: HausaufbauBody?, gen: JsonGenerator?, provider: SerializerProvider?) {

        gen!!.writeStartObject()
        gen.writeStringField("geschosse", value!!.geschosse.value)
        gen.writeStringField("dach", value.dach.value)
        gen.writeStringField("art", value.art.value)
        gen.writeNumberField("standardstufe", value.standardstufe)
        gen.writeEndObject()

    }
}
