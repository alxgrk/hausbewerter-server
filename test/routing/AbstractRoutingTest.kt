package de.alxgrk.routing

import de.alxgrk.module
import io.ktor.server.testing.withTestApplication
import models.table.Questionnaires
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before

abstract class AbstractRoutingTest {

    @Before
    fun setup() {
        // already start app
        withTestApplication({
            module(testing = true)

            // and delete all questionnaires
            transaction { Questionnaires.deleteAll() }
        }) {}
    }

}