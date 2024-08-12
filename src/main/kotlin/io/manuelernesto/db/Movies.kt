package io.manuelernesto.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  12/08/24 11:12 PM
 * @version 1.0
 */
object Movies: Table("tbl_movies") {
    val id = integer("id").autoIncrement()
    val title: Column<String> = varchar("title", 255)
    val year: Column<Int> = integer("year")

    val actor: Column<Int?> = (integer(name = "actor_id").references(Actors.id)).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}