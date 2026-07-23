package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String,
    val slug: String
)

@Serializable
data class Store(
    val id: Int,
    val name: String,
    val slug: String
)

@Serializable
data class Tag(
    val id: Int,
    val name: String,
    val slug: String
)

@Serializable
data class Screenshot(
    val id: Int,
    val imageUrl: String
)

@Serializable
enum class EsrbRating {
    EVERYONE,
    TEEN,
    MATURE
    //Añadir mas en caso de necesitarlo...
}

@Serializable
data class Platform(
    val id: Int,
    val name: String,
    val slug: String
)

@Serializable
data class Publisher(
    val id: Int,
    val name: String,
    val slug: String
)