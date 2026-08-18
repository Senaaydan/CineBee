package com.senaaydan.cinebee_

import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.presentation.search.MovieSearchIntent
import com.senaaydan.cinebee_.presentation.search.MovieSearchViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieSearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeMovieRepository
    private lateinit var viewModel: MovieSearchViewModel

    @Before
    fun setup() {
        fakeRepository = FakeMovieRepository()
        viewModel = MovieSearchViewModel(fakeRepository)
    }

    @Test
    fun `search query changed updates search results`() = runTest {


        val movie = Movie(
            id = 1,
            title = "Batman",
            imageUrl = "",
            imdb = 8.0,
            year = "2022",
            duration = "",
            description = "",
            genre = ""
        )

        fakeRepository.searchResult = listOf(movie)


        viewModel.onIntent(
            MovieSearchIntent.SearchQueryChanged("Batman")
        )

        advanceUntilIdle()


        assertEquals(
            "Batman",
            viewModel.state.value.searchQuery
        )

        assertEquals(
            listOf(movie),
            viewModel.state.value.searchResults
        )
    }
    @Test
    fun `search submitted saves current query`() = runTest {

        // Arrange
        viewModel.onIntent(
            MovieSearchIntent.SearchQueryChanged("Batman")
        )

        advanceUntilIdle()


        viewModel.onIntent(
            MovieSearchIntent.SearchSubmitted
        )

        advanceUntilIdle()


        assertEquals(
            "Batman",
            fakeRepository.savedSearchQuery
        )
    }
    @Test
    fun `delete recent search removes selected query`() = runTest {


        viewModel.onIntent(
            MovieSearchIntent.DeleteRecentSearch("Batman")
        )

        advanceUntilIdle()

        assertEquals(
            "Batman",
            fakeRepository.deletedSearchQuery
        )
    }

}