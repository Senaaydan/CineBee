package com.senaaydan.cinebee_

import androidx.lifecycle.viewmodel.compose.viewModel
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.presentation.favorites.FavoritesIntent
import com.senaaydan.cinebee_.presentation.favorites.FavoritesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeMovieRepository
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        fakeRepository = FakeMovieRepository()
    }

@Test
fun `favorite movies are loaded into state`() = runTest {

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

    fakeRepository.setFavoriteMovies(
        listOf(movie)
    )


    viewModel = FavoritesViewModel(fakeRepository)

    advanceUntilIdle()


    assertEquals(
        listOf(movie),
        viewModel.state.value.favoriteMovies
    )

    assertEquals(
        listOf(movie),
        viewModel.state.value.filteredFavorites
    )
}

    @Test
    fun `remove favorite clicked removes selected movie`() = runTest {

        viewModel = FavoritesViewModel(fakeRepository)


        viewModel.onIntent(
            FavoritesIntent.RemoveFavoriteClicked(1)
        )

        advanceUntilIdle()


        assertEquals(
            1,
            fakeRepository.removedFavoriteId
        )
    }
    @Test
    fun `search query changed filters favorite movies`() = runTest {

        val batman = Movie(
            id = 1,
            title = "Batman",
            imageUrl = "",
            imdb = 8.0,
            year = "2022",
            duration = "",
            description = "",
            genre = ""
        )

        val interstellar = Movie(
            id = 2,
            title = "Interstellar",
            imageUrl = "",
            imdb = 8.5,
            year = "2014",
            duration = "",
            description = "",
            genre = ""
        )

        fakeRepository.setFavoriteMovies(
            listOf(batman, interstellar)
        )

        viewModel = FavoritesViewModel(fakeRepository)

        advanceUntilIdle()

        viewModel.onIntent(
            FavoritesIntent.SearchQueryChanged("Bat")
        )


        assertEquals(
            "Bat",
            viewModel.state.value.searchQuery
        )

        assertEquals(
            listOf(batman),
            viewModel.state.value.filteredFavorites
        )
    }

    @Test
    fun `movie clicked emits navigate to detail event`() = runTest {


        viewModel = FavoritesViewModel(fakeRepository)

        var receivedEvent: NavigationEvent? = null

        val job = launch {
            viewModel.navigationEvent.collect { event ->
                receivedEvent = event
            }
        }


        viewModel.onIntent(
            FavoritesIntent.MovieClicked(7)
        )

        advanceUntilIdle()

        //bekledigimiz sonucla gercek sonucu karşılaştırıyor
        assertEquals(
            NavigationEvent.NavigateToDetail(7),
            receivedEvent
        )

        job.cancel()
    }




}