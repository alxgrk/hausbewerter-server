package de.alxgrk.di

import de.alxgrk.data.DaoQuestionnaireRepo
import de.alxgrk.data.QuestionnaireRepository
import org.h2.jdbcx.JdbcDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import javax.sql.DataSource

val productionKodein = Kodein.Module(name = "production", init = {

    bind<QuestionnaireRepository>() with singleton { DaoQuestionnaireRepo() }

    bind<DataSource>() with singleton {
        JdbcDataSource().apply {
            setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
            user = "sa"
            password = "sa"
        }
    }

})