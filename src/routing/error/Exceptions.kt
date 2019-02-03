package de.alxgrk.routing.error

import javax.xml.ws.http.HTTPException

class NotFoundException(override val message: String?): HTTPException(404)