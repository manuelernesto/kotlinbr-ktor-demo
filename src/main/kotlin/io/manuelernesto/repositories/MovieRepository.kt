package io.manuelernesto.repositories

import io.manuelernesto.db.Actors
import io.manuelernesto.db.DBFactory.dbQuery
import io.manuelernesto.db.Movies
import io.manuelernesto.model.Actor
import io.manuelernesto.model.Movie
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.update

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  12/08/24 11:20 PM
 * @version 1.0
 */

class MovieRepository {

    suspend fun save(movie: Movie): Movie? = dbQuery {
        val inserted = Movies.insert {
            it[title] = movie.title
            it[year] = movie.year
        }

        inserted.resultedValues?.singleOrNull()?.let {
            resultRowToMovie(it)
        }
    }

    suspend fun saveWithAuthor(movie: Movie): Movie? = dbQuery {
        val inserted = Movies.insert {
            it[title] = movie.title
            it[year] = movie.year
            it[actor] = movie.actor?.id
        }

        inserted.resultedValues?.singleOrNull()?.let {
            resultRowToMovieWithoutActor(it)
        }
    }

    suspend fun getMovies() = dbQuery {
        Movies.selectAll().map { resultRowToMovie(it) }
    }

    suspend fun getMoviesWithOrder(sortby: String?, direction: String?) = dbQuery {
        (Movies innerJoin Actors).selectAll().orderBy(getSortby(sortby), getDirection(direction)).map { resultRowToMovie(it) }
    }

    suspend fun getMovie(id: Int): Movie? = dbQuery {
        Movies.selectAll().where { Movies.id eq id }.map { resultRowToMovie(it) }.singleOrNull()
    }

    suspend fun delete(id: Int) = dbQuery {
        Movies.deleteWhere { Movies.id eq id }
    }

    suspend fun update(id: Int, movie: Movie) = dbQuery {
        Movies.update({ Movies.id eq id }) {
            it[title] = movie.title
            it[year] = movie.year
        }
    }

    private fun getSortby(sortedTitle: String?) = when (sortedTitle) {
        "title" -> Movies.title
        "year" -> Movies.year
        else -> Movies.id
    }

    private fun getDirection(direction: String?) = when (direction) {
        "asc" -> SortOrder.ASC
        else -> SortOrder.DESC
    }

    private fun resultRowToMovie(resultRow: ResultRow): Movie = Movie(
        id = resultRow[Movies.id],
        title = resultRow[Movies.title],
        year = resultRow[Movies.year],
        actor = Actor(
            id = resultRow[Actors.id].value,
            name = resultRow[Actors.name]
        )
    )

    private fun resultRowToMovieWithoutActor(resultRow: ResultRow): Movie = Movie(
        id = resultRow[Movies.id],
        title = resultRow[Movies.title],
        year = resultRow[Movies.year]
    )

}