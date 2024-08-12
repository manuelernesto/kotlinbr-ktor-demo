package io.manuelernesto.model

import kotlinx.serialization.Serializable

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  13/08/24 12:04 AM
 * @version 1.0
 */

@Serializable
data class Actor(
    val id: Int? = null,
    val name: String? = null,
)
