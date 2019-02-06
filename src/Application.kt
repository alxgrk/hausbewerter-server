package de.alxgrk

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import data.DatabaseFactory
import de.alxgrk.data.QuestionnaireRepository
import de.alxgrk.di.productionKodein
import de.alxgrk.di.testKodein
import de.alxgrk.routing.collection
import de.alxgrk.routing.questionnaire
import de.alxgrk.routing.root
import de.alxgrk.routing.single
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.slf4j.event.Level
import routing.error.statusConfig

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val kodein =
        if (testing)
            Kodein { import(testKodein, allowOverride = true) }
        else
            Kodein { import(productionKodein) }

    DatabaseFactory.init(kodein)
    val qRepo by kodein.instance<QuestionnaireRepository>()

    features()

    routing {
        root()
        collection(qRepo)
        single(qRepo)
        questionnaire(qRepo)
    }
}

private fun Application.features() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
    }

    install(AutoHeadResponse)

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        host("localhost:8088")
        host("alxgrk.github.io", schemes = listOf("https"))

        method(HttpMethod.Put)

        //header(HttpHeaders.Authorization)

        //allowCredentials = true
    }

    install(Compression)
    install(DefaultHeaders)

    install(StatusPages) { statusConfig() }
}
