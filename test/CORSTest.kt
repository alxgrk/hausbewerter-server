package de.alxgrk

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.*

class CORSTest {

    @Test
    fun testNoOriginHeader() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertNull(response.headers[HttpHeaders.AccessControlAllowOrigin])
            }
        }
    }

    @Test
    fun testWrongOriginHeader() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/") {
                addHeader(HttpHeaders.Origin, "invalid-host")
            }.let { call ->
                assertEquals(HttpStatusCode.OK, call.response.status())
                assertNull(call.response.headers[HttpHeaders.AccessControlAllowOrigin])
            }
        }
    }

    @Test
    fun testLocalFrontendRequest() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/") {
                addHeader(HttpHeaders.Origin, "http://localhost:8088")
            }.let { call ->
                assertEquals(HttpStatusCode.OK, call.response.status())
                assertEquals("http://localhost:8088", call.response.headers[HttpHeaders.AccessControlAllowOrigin])
            }
        }
    }

    @Test
    fun testLocalPreFlight() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Options, "/") {
                addHeader(HttpHeaders.Origin, "http://localhost:8088")
                addHeader(HttpHeaders.AccessControlRequestMethod, "GET")
            }.let { call ->
                assertEquals(HttpStatusCode.OK, call.response.status())
                assertEquals("http://localhost:8088", call.response.headers[HttpHeaders.AccessControlAllowOrigin])
            }
        }
    }

    @Test
    fun testRemoteFrontendRequest() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/") {
                addHeader(HttpHeaders.Origin, "https://alxgrk.github.io")
            }.let { call ->
                assertEquals(HttpStatusCode.OK, call.response.status())
                assertEquals("https://alxgrk.github.io", call.response.headers[HttpHeaders.AccessControlAllowOrigin])
            }
        }
    }

    @Test
    fun testRemotePreFlight() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Options, "/") {
                addHeader(HttpHeaders.Origin, "https://alxgrk.github.io")
                addHeader(HttpHeaders.AccessControlRequestMethod, "GET")
            }.let { call ->
                assertEquals(HttpStatusCode.OK, call.response.status())
                assertEquals("https://alxgrk.github.io", call.response.headers[HttpHeaders.AccessControlAllowOrigin])
            }
        }
    }
}