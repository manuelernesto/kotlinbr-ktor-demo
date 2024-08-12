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
import io.manuelernesto.repositories.MovieRepository
import kotlin.text.toInt

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  11/08/24 8:20 PM
 * @version 1.0
 */

fun Route.movieRouting(repository: MovieRepository) {
    get("/movies") {
        val sortby = call.request.queryParameters["sortby"]
        val direction = call.request.queryParameters["direction"]

        val response = repository.getMoviesWithOrder(sortby?.lowercase(), direction?.lowercase())

        if (response.isEmpty())
            call.respondText("No movies in database!")
        else
            call.respond(response)
    }

    post("/movies") {
        val movie = call.receive<Movie>()
        val response = repository.saveWithAuthor(movie)
        call.respond(status = HttpStatusCode.Created, message = response as Any)
    }

    get("/movies/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val movie = repository.getMovie(id.toInt()) ?: call.respond(HttpStatusCode.NotFound)
        call.respond(movie)
    }


    put("/movies/{id}") {
        val movie = call.receive<Movie>()
        val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)

        val response = repository.update(id.toInt(), movie)

        if (response > 0) {
            call.respondText("Movie successfully updated", status = HttpStatusCode.OK)
        } else {
            call.respondText("Movie not found!", status = HttpStatusCode.NotFound)
        }
    }


    delete("/movies/{id}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

        if (repository.delete(id.toInt()) > 0) {
            call.respondText("Movie deleted", status = HttpStatusCode.NoContent)
        } else {
            call.respondText("Movie not found!", status = HttpStatusCode.NotFound)
        }

    }
}