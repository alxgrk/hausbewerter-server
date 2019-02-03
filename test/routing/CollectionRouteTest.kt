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


class CollectionRouteTest {

    @Test
    fun testSingle() {
        withTestApplication({
            module(testing = true)

            // setup
            transaction {
                Questionnaires.deleteAll()
                Questionnaire.new(123) {
                    name = "test"
                    state = QuestionnaireState.OPEN
                }
                commit()
                Questionnaire.new(234) {
                    name = "test2"
                    state = QuestionnaireState.OPEN
                }
            }

        }) {
            handleRequest(HttpMethod.Get, "/fragebogen").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                assertThat(response.content).isEqualToIgnoringWhitespaces(
                    """
                    {
                        "members": [
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
                                        }
                                    ]
                                }
                            },
                            {
                                "id": "234",
                                "name": "test2",
                                "state": "OPEN",
                                "_schema": {
                                    "links": [
                                        {
                                            "rel": "self",
                                            "href": "/fragebogen/234",
                                            "method": "GET"
                                        }
                                    ]
                                }
                            }
                        ],
                        "_schema": {
                            "links": [
                                {
                                    "rel": "self",
                                    "href": "/fragebogen",
                                    "method": "GET"
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
    fun testNew() {
        withTestApplication({
            module(testing = true)

            // setup
            transaction {
                Questionnaires.deleteAll()
            }
        }) {
            handleRequest(HttpMethod.Post, "/fragebogen").apply {

                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                assertThat(response.content).isEqualToIgnoringWhitespaces(
                    """
                    {
                        "id": "1",
                        "_schema": {
                            "links": [
                                {
                                    "rel": "self",
                                    "href": "/fragebogen/1",
                                    "method": "GET"
                                }
                            ]
                        }
                    }
                """.trimIndent()
                )
            }
        }
    }
}