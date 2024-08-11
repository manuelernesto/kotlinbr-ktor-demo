package io.manuelernesto.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.manuelernesto.model.Movie
import io.manuelernesto.model.db
import kotlin.text.toInt

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  11/08/24 8:20 PM
 * @version 1.0
 */


fun Route.movieRouting() {
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