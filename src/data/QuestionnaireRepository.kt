package de.alxgrk.data

import de.alxgrk.models.calculation.calculatedBaujahr
import de.alxgrk.models.calculation.kostenkennwertFor
import de.alxgrk.models.calculation.nutzungsdauerNachStandardstufe
import de.alxgrk.models.entity.Besonderes
import de.alxgrk.models.entity.Questionnaire
import de.alxgrk.models.entity.Sanierungen
import de.alxgrk.models.web.mapping.toDbEntity
import de.alxgrk.routing.Routes
import de.alxgrk.routing.error.PreviousQuestionnaireStepMissingException
import de.alxgrk.routing.error.UnsupportedStandardstufeException
import models.table.QuestionnaireState
import models.table.Questionnaires
import models.web.HausaufbauBody.*
import models.web.toDbEntity
import org.jetbrains.exposed.sql.EqOp
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.intLiteral
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.random.Random

interface QuestionnaireRepository {

    fun exists(id: Int): Boolean

    fun getAll(): SizedIterable<Questionnaire>

    fun getOne(id: Int): Questionnaire

    fun create(): Questionnaire

    fun calculateFlaeche(id: Int, breite: BigDecimal, laenge: BigDecimal, ebenen: Int): BigDecimal

    fun calculateHausaufbau(
        id: Int,
        geschosse: Geschosse,
        dach: Dach,
        art: Art,
        standardstufe: Int
    ): BigDecimal

    fun calculateHerstellungswert(id: Int, baupreisindex: BigDecimal, aussenanlage: BigDecimal): BigDecimal

    fun calculateSachwert(id: Int, baujahr: Int, sanierungen: Sanierungen): Triple<Int, Int, BigDecimal>

    fun calculateBesonderes(id: Int, besonderes: List<Pair<String, BigDecimal>>): BigDecimal

    fun calculateMarktanpassungsfaktor(id: Int, marktanpassungsfaktor: BigDecimal): BigDecimal

    fun calculateGrundstuecksgroesse(id: Int, grundstuecksgroesse: BigDecimal): BigDecimal
}

class DaoQuestionnaireRepo : QuestionnaireRepository {

    override fun exists(id: Int): Boolean = Questionnaire.count(EqOp(Questionnaires.id, intLiteral(id))) == 1

    override fun getAll() = Questionnaire.all()

    override fun getOne(id: Int) = Questionnaire[id]

    override fun create() = Questionnaire.new {
        name = "Test" // FIXME
        state = QuestionnaireState.OPEN
    }

    override fun calculateFlaeche(id: Int, breite: BigDecimal, laenge: BigDecimal, ebenen: Int): BigDecimal =
        Questionnaire[id].run {
            this.breite = breite
            this.laenge = laenge
            this.ebenen = ebenen

            this.wohnflaeche = breite.multiply(laenge).multiply(BigDecimal(ebenen))

            return@run this.wohnflaeche!!
        }

    override fun calculateHausaufbau(
        id: Int,
        geschosse: Geschosse,
        dach: Dach,
        art: Art,
        standardstufe: Int
    ): BigDecimal = Questionnaire[id].run {
        this.geschosse = geschosse.toDbEntity()
        this.dach = dach.toDbEntity()
        this.art = art.toDbEntity()

        if (standardstufe in 1..5)
            this.standardstufe = standardstufe
        else
            throw UnsupportedStandardstufeException(id.toString(), standardstufe)

        val kostenkennwert = kostenkennwertFor(this.geschosse!!, this.dach!!, this.art!!, this.standardstufe!!)

        if (this.wohnflaeche == null)
            throw PreviousQuestionnaireStepMissingException(
                "Nächster Schritt kann nicht berechnet werden, die Wohnfläche wurde noch nicht berechnet.",
                this.id.toString(),
                Routes.HAUSAUFBAU
            )

        this.zwischenergebnisHausaufbau = this.wohnflaeche!!.multiply(kostenkennwert.toBigDecimal())

        return@run this.zwischenergebnisHausaufbau!!
    }

    override fun calculateHerstellungswert(id: Int, baupreisindex: BigDecimal, aussenanlage: BigDecimal): BigDecimal =
        Questionnaire[id].run {
            this.baupreisindex = baupreisindex
            this.aussenanlage = aussenanlage

            if (this.zwischenergebnisHausaufbau == null)
                throw PreviousQuestionnaireStepMissingException(
                    "Nächster Schritt kann nicht berechnet werden, der Hausaufbau wurde noch nicht berücksichtigt.",
                    this.id.toString(),
                    Routes.HERSTELLUNGSWERT
                )

            this.herstellungswert = this.zwischenergebnisHausaufbau!!.multiply(baupreisindex).add(aussenanlage)

            return@run this.herstellungswert!!
        }

    override fun calculateSachwert(id: Int, baujahr: Int, sanierungen: Sanierungen): Triple<Int, Int, BigDecimal> =
        Questionnaire[id].run {
            this.baujahr = baujahr
            this.sanierungDach = sanierungen.dach
            this.sanierungAussenwaende = sanierungen.aussenwaende
            this.sanierungFensterUndAussentueren = sanierungen.fensterUndAussentueren
            this.sanierungSonstigeTechnischeAusstattung = sanierungen.sonstigeTechnischeAusstattung
            this.sanierungFussboden = sanierungen.fussboden
            this.sanierungHeizung = sanierungen.heizung

            this.calculatedBaujahr = sanierungen.calculatedBaujahr(baujahr)

            val currentNutzungsdauer = Calendar.getInstance()[Calendar.YEAR] - calculatedBaujahr!!

            if (this.standardstufe == null)
                throw UnsupportedStandardstufeException(id.toString(), this.standardstufe)

            val gesamtnutzungsdauer = nutzungsdauerNachStandardstufe(id, this.standardstufe!!)
            this.restnutzungsdauer = gesamtnutzungsdauer - currentNutzungsdauer

            val alterswertminderungAsPercentage =
                currentNutzungsdauer.toBigDecimal()
                    .divide(gesamtnutzungsdauer.toBigDecimal(), 5, RoundingMode.HALF_EVEN)

            if (this.herstellungswert == null)
                throw PreviousQuestionnaireStepMissingException(
                    "Nächster Schritt kann nicht berechnet werden, der Herstellungswert wurde noch nicht berechnet.",
                    this.id.toString(),
                    Routes.SACHWERT
                )

            this.vorlaeufigerSachwert =
                    this.herstellungswert!! - (this.herstellungswert!!.multiply(alterswertminderungAsPercentage))

            return@run Triple(calculatedBaujahr!!, restnutzungsdauer!!, vorlaeufigerSachwert!!)
        }

    override fun calculateBesonderes(id: Int, besonderes: List<Pair<String, BigDecimal>>): BigDecimal =
        Questionnaire[id].run {

            if (this.vorlaeufigerSachwert == null)
                throw PreviousQuestionnaireStepMissingException(
                    "Nächster Schritt kann nicht berechnet werden, der vorläufige Sachwert wurde noch nicht berechnet.",
                    this.id.toString(),
                    Routes.BESONDERES
                )

            // TODO only update, not replace
            this.besonderes.forEach { it.delete() }
            besonderes.forEach { (name, value) ->
                Besonderes.new {
                    this.questionnaire = this@run
                    this.name = name
                    this.value = value
                }
            }

            this.vorlaeufigerSachwertMitBesonderem = besonderes.map { it.second }
                .fold(this.vorlaeufigerSachwert!!) { z, cur -> z.add(cur) }

            return@run this.vorlaeufigerSachwertMitBesonderem!!
        }

    override fun calculateMarktanpassungsfaktor(id: Int, marktanpassungsfaktor: BigDecimal): BigDecimal =
        Questionnaire[id].run {
            this.marktanpassungsfaktor = marktanpassungsfaktor

            if (this.vorlaeufigerSachwertMitBesonderem == null)
                throw PreviousQuestionnaireStepMissingException(
                    "Nächster Schritt kann nicht berechnet werden, die Besonderheiten des Hauses wurden noch nicht berücksichtigt.",
                    this.id.toString(),
                    Routes.MARKTANPASSUNGSFAKTOR
                )

            this.vorlaeufigerSachwertMitMarktanpassungsfaktor =
                    this.vorlaeufigerSachwertMitBesonderem!!.multiply(marktanpassungsfaktor)

            return@run this.vorlaeufigerSachwertMitMarktanpassungsfaktor!!
        }

    override fun calculateGrundstuecksgroesse(id: Int, grundstuecksgroesse: BigDecimal): BigDecimal =
        Questionnaire[id].run {
            this.grundstuecksgroesse = grundstuecksgroesse

            if (this.vorlaeufigerSachwertMitMarktanpassungsfaktor == null)
                throw PreviousQuestionnaireStepMissingException(
                    "Nächster Schritt kann nicht berechnet werden, der Marktanpassungsfaktor wurde noch nicht berücksichtigt.",
                    this.id.toString(),
                    Routes.GRUNDSTUECKSWERT
                )

            val bodenrichtwert = 1000 // TODO find correct values
            val abweichenderBodenrichtwert = bodenrichtwert * 1 // TODO find correct value

            this.grundstueckswert = this.grundstuecksgroesse!!.multiply(abweichenderBodenrichtwert.toBigDecimal())

            this.gesamtwert = this.vorlaeufigerSachwertMitMarktanpassungsfaktor!!.add(this.grundstueckswert!!)

            return@run this.gesamtwert!!
        }

}
