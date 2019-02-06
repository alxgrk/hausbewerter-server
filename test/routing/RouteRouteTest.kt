package de.alxgrk.routing

import assertk.assertThat
import assertk.assertions.isEqualTo
import de.alxgrk.isEqualToIgnoringWhitespaces
import de.alxgrk.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test


class RouteRouteTest : AbstractRoutingTest() {

    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                assertThat(response.content).isEqualToIgnoringWhitespaces("""
                    {
                        "_schema": {
                            "links": [
                                {
                                    "rel": "self",
                                    "href": "/",
                                    "method": "GET"
                                },
                                {
                                    "rel": "create",
                                    "href": "/fragebogen",
                                    "method": "POST"
                                },
                                {
                                    "rel": "get-by-id",
                                    "href": "/fragebogen/{id}",
                                    "method": "GET"
                                }
                            ]
                        }
                    }
                """.trimIndent())
            }
        }
    }

}