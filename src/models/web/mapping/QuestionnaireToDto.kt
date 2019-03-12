package de.alxgrk.models.web.mapping

import de.alxgrk.models.entity.Questionnaire
import models.web.*
import org.jetbrains.exposed.sql.transactions.transaction

inline fun <reified T> Questionnaire.toDto(): T? = when (T::class) {

    BesonderesBody::class -> transaction {
        if (!besonderes.empty())
            BesonderesBody(
                Besonderes(
                    besonderes.map { BesonderesSachwerte(it.name, it.value) })
            ) as T
        else null
    }

    FlaecheBody::class ->
        if (breite != null && laenge != null && ebenen != null)
            FlaecheBody(breite!!, laenge!!, ebenen!!) as T
        else null

    GrundstueckswertBody::class ->
        if (grundstuecksgroesse != null) GrundstueckswertBody(grundstuecksgroesse!!) as T
        else null

    HausaufbauBody::class ->
        if (geschosse != null && dach != null && art != null && standardstufe != null)
            HausaufbauBody(geschosse!!.toDto(), dach!!.toDto(), art!!.toDto(), standardstufe!!) as T
        else null

    HerstellungswertBody::class ->
        if (baupreisindex != null && aussenanlage != null)
            HerstellungswertBody(baupreisindex!!, aussenanlage!!) as T
        else null

    MarktanpassungsfaktorBody::class ->
        if (marktanpassungsfaktor != null) MarktanpassungsfaktorBody(marktanpassungsfaktor!!) as T
        else null

    SachwertBody::class ->
        if (baujahr != null && sanierungDach != null
            && sanierungAussenwaende != null && sanierungFensterUndAussentueren != null
            && sanierungSonstigeTechnischeAusstattung != null
            && sanierungFussboden != null && sanierungHeizung != null
        )
            SachwertBody(
                baujahr!!,
                Sanierungen(
                    sanierungDach!!, sanierungAussenwaende!!,
                    sanierungFensterUndAussentueren!!, sanierungSonstigeTechnischeAusstattung!!,
                    sanierungFussboden!!, sanierungHeizung!!
                )
            ) as T
        else null

    else -> null
}