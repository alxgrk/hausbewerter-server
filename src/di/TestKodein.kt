package de.alxgrk.di

import org.h2.jdbcx.JdbcDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import javax.sql.DataSource

val testKodein = Kodein.Module(name = "testing", allowSilentOverride = true, init = {

    importOnce(productionKodein)

    bind<DataSource>() with singleton {
        JdbcDataSource().apply {
            setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
            user = "sa"
            password = "sa"
        }
    }

})