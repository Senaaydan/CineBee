package com.senaaydan.cinebee_.di

import android.content.Context
import androidx.room.Room
import com.senaaydan.cinebee_.data.local.database.CineBeeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides // biri database isterse bu fonksiyonu çalıştır demek
    @Singleton  //database i 1 kere oluştur demek
    fun provideDatabase(@ApplicationContext context: Context): CineBeeDatabase {
        return Room.databaseBuilder(
            context,
            CineBeeDatabase::class.java,
            "cinebee_database"
        ).build()
    }
    @Provides
    fun provideFavoriteMovieDao(database: CineBeeDatabase) = database.favoriteMovieDao()
    @Provides
    fun provideSearchHistoryDao(database: CineBeeDatabase) = database.searchHistoryDao()
}

