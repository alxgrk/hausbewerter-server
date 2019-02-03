package de.alxgrk.routing

import assertk.assertThat
import assertk.assertions.isEqualTo
import de.alxgrk.isEqualToIgnoringWhitespaces
import de.alxgrk.models.entity.Questionnaire
import de.alxgrk.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import models.table.QuestionnaireState
import models.table.Questionnaires
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.Test


class SingleRouteTest {

    private val id = 123

    @Test
    fun testSingle() {
        withTestApplication({
            module(testing = true)

            // setup
            transaction {
                Questionnaires.deleteAll()
                Questionnaire.new(id) {
                    name = "test"
                    state = QuestionnaireState.OPEN
                }
            }

        }) {
            handleRequest(HttpMethod.Get, "/fragebogen/$id").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                assertThat(response.content).isEqualToIgnoringWhitespaces(
                    """
                    {
                        "id": "123",
                        "name": "test",
                        "state": "OPEN",
                        "_schema": {
                            "links": [
                                {
                                    "rel": "self",
                                    "href": "/fragebogen/123",
                                    "method": "GET"
                                },
                                {
                                    "rel": "next",
                                    "href": "/fragebogen/123/flaeche",
                                    "method": "PUT",
                                    "targetSchema": {
                                        "${'$'}ref": "https://api.swaggerhub.com/domains/hausbewerter/hausbewerter-domain/1.0.0#/definitions/FlaecheBody"
                                    }
                                }
                            ]
                        }
                    }
                """.trimIndent()
                )
            }
        }
    }

    @Test
    fun testNotFound() {

        withTestApplication({
            module(testing = true)

            // setup
            transaction {
                Questionnaires.deleteAll()
            }
        }) {
            handleRequest(HttpMethod.Get, "/fragebogen/$id").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.NotFound)

                assertThat(response.content).isEqualToIgnoringWhitespaces(
                    """
                    {
                        "cause": "Questionnaires with ID '123' not found."
                    }
                """.trimIndent()
                )
            }
        }
    }

    @Test
    fun testNoNumber() {
        val letterId = "abc"

        withTestApplication({
            module(testing = true)

            // setup
            transaction {
                Questionnaires.deleteAll()
            }
        }) {
            handleRequest(HttpMethod.Get, "/fragebogen/$letterId").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.NotFound)

                assertThat(response.content).isEqualToIgnoringWhitespaces(
                    """
                    {
                        "cause": "Questionnaires with ID '$letterId' not found."
                    }
                """.trimIndent()
                )
            }
        }
    }
}