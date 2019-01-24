package de.alxgrk

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import de.alxgrk.routing.*
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.routing.routing
import org.slf4j.event.Level
import routing.questionnaire.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    install(AutoHeadResponse)

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        host("localhost:8088")
        host("alxgrk.github.io")

        method(HttpMethod.Put)

        //header(HttpHeaders.Authorization)

        //allowCredentials = true
    }

    install(Compression)

    install(DefaultHeaders) {}

    routing {
        root()
        collection()
        single()
        questionnaire()
    }
}

