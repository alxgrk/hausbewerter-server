package data

import de.alxgrk.models.entity.Questionnaire
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.table.QuestionnaireState
import models.table.Questionnaires
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import javax.sql.DataSource

object DatabaseFactory {

    fun init(kodein: Kodein) {

        val ds by kodein.instance<DataSource>()
        Database.connect(ds)

        transaction {
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            // create tables
            create(Questionnaires)

            // save initial data if necessary
            if(Questionnaire.count() == 0) {
                Questionnaire.new {
                    name = "questionnaire one"
                    state = QuestionnaireState.OPEN
                }
            }
        }
    }

}