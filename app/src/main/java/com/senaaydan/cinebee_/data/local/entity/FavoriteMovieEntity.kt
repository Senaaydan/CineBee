package com.senaaydan.cinebee_.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val imdb: Double,
    val year: String,
    val duration: String,
    val description: String,
    val genre:String

)