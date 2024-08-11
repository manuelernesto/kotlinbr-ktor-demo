package io.manuelernesto.model

import kotlinx.serialization.Serializable

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  11/08/24 8:23 PM
 * @version 1.0
 */

@Serializable
data class Movie(val id: Int, val title: String, val year: Int)

val db = mutableListOf<Movie>()