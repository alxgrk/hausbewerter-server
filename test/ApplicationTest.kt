package de.alxgrk

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun testAppStarts() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Head, "/").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

            }
        }
    }

}
