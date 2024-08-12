package io.manuelernesto.db

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  13/08/24 12:05 AM
 * @version 1.0
 */
object Actors: IntIdTable() {
     val name = varchar("name", 255)
}