package com.pmdm.mygamestore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val slug: String? = null,
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val rating: Double,
    val releaseDate: String,
    val category: GameCategory,
    val platforms: List<Platform> = emptyList(),
    val genres: List<GameCategory> = emptyList(),
    val stores: List<Store> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val screenshots: List<Screenshot> = emptyList(),
    val metacritic: Int? = null,
    val playtime: Int? = null,
    val ratingsCount: Int? = null,
    val esrbRating: EsrbRating? = null,

    //Nuevos campos para el detalle
    val developers: List<Publisher> = emptyList(),
    val publishers: List<Publisher> = emptyList(),
    val movies: List<String> = emptyList() //Para el boton de "watch trailer"
)