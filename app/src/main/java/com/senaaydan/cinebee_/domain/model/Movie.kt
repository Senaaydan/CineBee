package com.senaaydan.cinebee_.domain.model

data class Actor(
    val name : String,
    val characterName : String,
    val imageUrl : String
)

data class Movie(
    val id : Int ,
    val title: String,
    val imageUrl : String,
    val imdb: Double = 0.0,
    val year: String ="2023",
    val duration: String ="120 dk",
    val description: String ="film konusu",
    val cast: List<Actor> =emptyList(),
    val genre: String

)

data class Category(
    val id : Int,
    val title: String,
    val movies : List<Movie>
)


