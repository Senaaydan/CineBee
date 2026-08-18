package com.senaaydan.cinebee_.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.senaaydan.cinebee_.data.local.entity.FavoriteMovieEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies")
     fun getAll(): Flow<List<FavoriteMovieEntity>> //otomatik güncellenmesi için flow

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun get(id: Int): FavoriteMovieEntity?
    }

