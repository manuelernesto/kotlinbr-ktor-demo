package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable


@Serializable
data class Movie(val id: Int, val title: String, val year: Int)

val db = mutableListOf<Movie>()


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello Kotlin 🇧🇷!")
        }

        get("/movies") {
            if (db.isEmpty())
                call.respondText("No movies in database!")
            else
                call.respond(db)
        }

        post("/movies") {
            val movie = call.receive<Movie>()
            db.add(movie)
            call.respondText("Movies added to database!", status = HttpStatusCode.Created)
        }

        get("/movies/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val movie = db.find { it.id == id.toInt() } ?: call.respond(HttpStatusCode.NotFound)
            call.respond(movie)
        }


        put("/movies/{id}") {
            val movie = call.receive<Movie>()
            val index = db.indexOfFirst { it.id == movie.id }
            if (index != -1) {
                db[index] = movie
                call.respondText("Movie successfully updated", status = HttpStatusCode.OK)
            } else {
                call.respondText("Movie not found!", status = HttpStatusCode.NotFound)
            }
        }


        delete("/movies/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

            if (db.removeIf { it.id == id.toInt() }) {
                call.respondText("Movie deleted", status = HttpStatusCode.NoContent)
            } else {
                call.respondText("Movie not found!", status = HttpStatusCode.NotFound)
            }

        }

    }
}
