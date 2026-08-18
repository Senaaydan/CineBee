package com.senaaydan.cinebee_.data.repository

import android.icu.util.ULocale.getLanguage
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.data.local.SettingsDataStore
import com.senaaydan.cinebee_.data.local.dao.FavoriteMovieDao
import com.senaaydan.cinebee_.data.local.dao.SearchHistoryDao
import com.senaaydan.cinebee_.data.local.entity.SearchHistoryEntity
import com.senaaydan.cinebee_.data.mapper.toActor
import com.senaaydan.cinebee_.data.mapper.toFavoriteMovieEntity
import com.senaaydan.cinebee_.data.mapper.toMovie
import com.senaaydan.cinebee_.data.remote.MovieApiService
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

  class MovieRepositoryImpl @Inject constructor(
      private val movieApiService: MovieApiService, private val searchHistoryDao: SearchHistoryDao,
      private val settingsDataStore: SettingsDataStore, private val favoriteMovieDao: FavoriteMovieDao) : MovieRepository {
     override suspend fun getCategories(): List<Category> {
         val language = getLanguageCode()
         val actionMovies = movieApiService
             .getMoviesByGenre(28,language=language)
             .results
             .map { it.toMovie() }

         val comedyMovies = movieApiService
             .getMoviesByGenre(35,language=language)
             .results
             .map { it.toMovie() }

         val dramaMovies = movieApiService
             .getMoviesByGenre(18,language=language)
             .results
             .map { it.toMovie() }

         val scienceFictionMovies = movieApiService
             .getMoviesByGenre(878,language=language)
             .results
             .map { it.toMovie() }

         return listOf(
             Category(
                 id = 1,
                 title = "Aksiyon",
                 movies = actionMovies
             ),
             Category(
                 id = 2,
                 title = "Komedi",
                 movies = comedyMovies
             ),
             Category(
                 id = 3,
                 title = "Dram",
                 movies = dramaMovies
             ),
             Category(
                 id = 4,
                 title = "Bilim Kurgu",
                 movies = scienceFictionMovies
             )
         )
     }
      private suspend fun getLanguageCode(): String {
          return settingsDataStore
              .languagePreferenceFlow
              .first()
              .apiCode
      }

   override suspend fun getMovieById(movieId: Int): Movie? {
       val movieDto = movieApiService.getMovieDetail(movieId, language = getLanguageCode())
       val credits = movieApiService.getMovieCredits(movieId, language = getLanguageCode())
       val cast = credits.cast.map { castDto ->
           castDto.toActor()
       }
       return movieDto.toMovie().copy(cast = cast )
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        val response = movieApiService.searchMovies(query)

        return response.results.map { movieDto ->
            movieDto.toMovie()
        }
    }
     override suspend fun addFavorite(movie: Movie){
          favoriteMovieDao.insert(movie.toFavoriteMovieEntity())
     }
    override suspend fun removeFavorite(movieId: Int){
         favoriteMovieDao.delete(movieId)
     }
     override suspend fun getFavorite(movieId:Int):Movie?{
         return favoriteMovieDao.get(movieId)?.toMovie()
     }

     override fun getFavoriteMovies(): Flow<List<Movie>> {
         return favoriteMovieDao.getAll().map { favoriteMovieEntities ->
             favoriteMovieEntities.map { favoriteMovieEntity ->
                 favoriteMovieEntity.toMovie()
             }
         }

     }
      override suspend fun saveSearch(searchQuery: String) {
          val searchHistoryEntity =
              SearchHistoryEntity(query = searchQuery, timestamp = System.currentTimeMillis())
          searchHistoryDao.insert(searchHistoryEntity)

      }
      override suspend fun clearAllSearches() {
          searchHistoryDao.deleteAll()
      }
      override suspend fun deleteRecentSearch(query: String) {
          searchHistoryDao.delete(query)
      }
      override suspend fun getRecentSearches(): Flow<List<String>> {
          return searchHistoryDao.getRecentSearches().map { searchHistoryList ->
              searchHistoryList.map { searchHistory ->
                  searchHistory.query
              }
          }
      }





}