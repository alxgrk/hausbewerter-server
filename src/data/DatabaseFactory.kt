package data

import models.table.Questionnaires
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
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

        }
    }

}