package com.senaaydan.cinebee_.data.local.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import com.senaaydan.cinebee_.data.local.dao.FavoriteMovieDao
import com.senaaydan.cinebee_.data.local.dao.SearchHistoryDao
import com.senaaydan.cinebee_.data.local.entity.FavoriteMovieEntity
import com.senaaydan.cinebee_.data.local.entity.SearchHistoryEntity

@Database(entities = [FavoriteMovieEntity::class,SearchHistoryEntity::class], version = 3)
abstract class CineBeeDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}