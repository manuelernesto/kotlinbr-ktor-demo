package io.manuelernesto.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.manuelernesto.model.Actor
import io.manuelernesto.repositories.ActorRepository

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  13/08/24 12:12 AM
 * @version 1.0
 */

fun Route.actorRouting(repository: ActorRepository) {

    post("/actors") {
        val actor = call.receive<Actor>()
        val response = repository.save(actor)
        call.respond(status = HttpStatusCode.Created, message = response as Any)
    }
}