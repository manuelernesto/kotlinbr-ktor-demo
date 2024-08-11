package io.manuelernesto

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.manuelernesto.plugins.configureRouting
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/health-check").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Up", bodyAsText())
        }
    }
}
