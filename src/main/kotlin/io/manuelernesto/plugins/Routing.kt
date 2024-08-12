package io.manuelernesto.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.manuelernesto.repositories.ActorRepository
import io.manuelernesto.repositories.MovieRepository
import io.manuelernesto.routing.actorRouting
import io.manuelernesto.routing.movieRouting

fun Application.configureRouting() {
    routing {
        get("/health-check") {
            call.respondText("Up")
        }
        movieRouting(MovieRepository())
        actorRouting(ActorRepository())
    }
}
