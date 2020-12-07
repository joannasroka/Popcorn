package com.example.popcorn.movie

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Movie(
    val name: String,
    val year: Int,
    val category: MovieCategory,
    val cast: List<String>,
    val description: String,
    val thumbnail: String,
    val images: List<String>,
    val favourite: Boolean = false
)
