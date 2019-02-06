package de.alxgrk.routing.error

import de.alxgrk.routing.Routes
import javax.xml.ws.http.HTTPException

class NotFoundException(override val message: String?) : HTTPException(404)

class PreviousQuestionnaireStepMissingException(override val message: String?, val id: String, val step: Routes) :
    RuntimeException()

class UnsupportedStandardstufeException(val id: String, standardstufe: Int?) :
    RuntimeException(
        if (standardstufe == null)
            "Standardstufe wurde noch nicht angegeben."
        else
            "Standardstufe '$standardstufe' muss zwischen 1 und 5 liegen."
    )
