package io.manuelernesto.repositories

import io.manuelernesto.db.Actors
import io.manuelernesto.db.DBFactory.dbQuery
import io.manuelernesto.model.Actor
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  13/08/24 12:08 AM
 * @version 1.0
 */
class ActorRepository {

    suspend fun save(actor: Actor): Actor? = dbQuery {
        val inserted = Actors.insert {
            it[name] = actor.name!!
        }

        inserted.resultedValues?.singleOrNull()?.let { resultRowToActor(it) }

    }

    private fun resultRowToActor(resultRow: ResultRow): Actor = Actor(
        id = resultRow[Actors.id].value,
        name = resultRow[Actors.name]
    )
}