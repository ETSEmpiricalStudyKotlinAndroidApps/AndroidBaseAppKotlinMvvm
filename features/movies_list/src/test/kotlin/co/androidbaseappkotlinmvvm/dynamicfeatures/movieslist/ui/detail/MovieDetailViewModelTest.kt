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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.core.network.responses.BaseResponse
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.MovieDetailViewModel
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.MovieDetailViewState
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.model.MovieDetail
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.model.MovieDetailMapper
import co.androidbaseappkotlinmvvm.libraries.testutils.rules.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var movieRepository: MovieRepository
    @MockK(relaxed = true)
    lateinit var movieFavoriteRepository: MovieFavoriteRepository
    @MockK
    lateinit var movieDetailMapper: MovieDetailMapper
    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<MovieDetailViewState>
    @MockK(relaxed = true)
    lateinit var dataObserver: Observer<MovieDetail>
    lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MovieDetailViewModel(
            movieRepository = movieRepository,
            movieFavoriteRepository = movieFavoriteRepository,
            movieDetailMapper = movieDetailMapper
        )
        viewModel.state.observeForever(stateObserver)
        viewModel.data.observeForever(dataObserver)
    }

    @Test
    fun loadCharacterDetail_ShouldSetLoadingState() {
        viewModel.loadMovieDetail(1L)

        verify { stateObserver.onChanged(MovieDetailViewState.Loading) }
    }

    @Test
    fun loadCharacterDetail_WhenError_ShouldBeErrorState() {
        viewModel.loadMovieDetail(1L)

        val expectedState: MovieDetailViewState = MovieDetailViewState.Error
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun loadCharacterDetail_WhenSuccess_ShouldPostDataResult() {
        val characterDetail = mockk<MovieDetail>()
        val characterResponse = mockk<BaseResponse<MovieResponse>>()
        coEvery { movieRepository.getMovie(any()) } returns characterResponse
        coEvery { movieDetailMapper.map(any()) } returns characterDetail

        val characterId = 1L
        viewModel.loadMovieDetail(characterId)

        verify { dataObserver.onChanged(characterDetail) }
        coVerify { movieRepository.getMovie(characterId) }
        coVerify { movieDetailMapper.map(characterResponse) }
    }

    @Test
    fun loadCharacterDetail_NonFavourite_WhenSuccess_ShouldBeAddToFavoriteState() {
        val characterDetail = mockk<MovieDetail>()
        coEvery { movieFavoriteRepository.getMovieFavorite(any()) } returns null
        coEvery { movieRepository.getMovie(any()) } returns mockk()
        coEvery { movieDetailMapper.map(any()) } returns characterDetail

        viewModel.loadMovieDetail(1L)

        val expectedState = MovieDetailViewState.AddToFavorite
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun loadCharacterDetail_Favourite_WhenSuccess_ShouldBeAlreadyAddedToFavoriteState() {
        val characterDetail = mockk<MovieDetail>()
        coEvery { movieFavoriteRepository.getMovieFavorite(any()) } returns mockk()
        coEvery { movieRepository.getMovie(any()) } returns mockk()
        coEvery { movieDetailMapper.map(any()) } returns characterDetail

        viewModel.loadMovieDetail(1L)

        val expectedState = MovieDetailViewState.AlreadyAddedToFavorite
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun addCharacterToFavorite_WhenNotLoadedDetail_ShouldDoNothing() {
        viewModel.addMovieToFavorite()

        coVerify(exactly = 0) {
            movieFavoriteRepository.insertMovieFavorite(any(), any(), any())
        }
        verify(exactly = 0) { stateObserver.onChanged(any()) }
    }

    @Test
    fun addCharacterToFavorite_WhenLoadedDetail_ShouldBeAddedToFavorite() {
        val characterDetail = MovieDetail(
            id = 1011334,
            name = "3-D Man",
            description = "",
            imageUrl = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg"
        )
        coEvery { movieRepository.getMovie(any()) } returns mockk()
        coEvery { movieDetailMapper.map(any()) } returns characterDetail

        viewModel.loadMovieDetail(1L)
        viewModel.addMovieToFavorite()

        val expectedState = MovieDetailViewState.AddedToFavorite
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
        coVerify {
            movieFavoriteRepository.insertMovieFavorite(
                id = characterDetail.id,
                name = characterDetail.name,
                imageUrl = characterDetail.imageUrl
            )
        }
    }

    @Test
    fun dismissCharacterDetail_ShouldBeDismissState() {
        viewModel.dismissMovieDetail()

        val expectedState = MovieDetailViewState.Dismiss
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }
}
