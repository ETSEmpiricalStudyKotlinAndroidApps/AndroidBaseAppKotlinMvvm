/*
 * Copyright 2020 tecruz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.libraries.testutils.rules.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesFavoriteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var repository: MovieFavoriteRepository
    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<MoviesFavoriteViewState>
    lateinit var viewModel: MoviesFavoriteViewModel

    private val data = MutableLiveData<List<MovieFavorite>>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every {
            repository.getAllMoviesFavoriteLiveData()
        } returns data

        viewModel = MoviesFavoriteViewModel(movieFavoriteRepository = repository)
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun initializeViewModel_ShouldObtainAllFavorite() {
        coVerify {
            repository.getAllMoviesFavoriteLiveData()
        }
    }

    @Test
    fun removeFavoriteMovie_ShouldInvokeRepositoryDeleteMethod() {
        val movie = mockk<MovieFavorite>()
        viewModel.removeFavoriteMovie(movie)

        coVerify {
            repository.deleteMovieFavorite(movie)
        }
    }

    @Test
    fun emptyMoviesFavorite_ShouldBeEmptyState() {
        data.postValue(listOf())

        val expectedState = MoviesFavoriteViewState.Empty
        assertEquals(expectedState, viewModel.state.value)
        coVerify {
            stateObserver.onChanged(expectedState)
        }
    }

    @Test
    fun addedMoviesFavorite_ShouldBeListedState() {
        val favoriteMovie = mockk<MovieFavorite>()
        data.postValue(listOf(favoriteMovie))

        val expectedState = MoviesFavoriteViewState.Listed
        assertEquals(expectedState, viewModel.state.value)
        coVerify {
            stateObserver.onChanged(expectedState)
        }
    }
}
