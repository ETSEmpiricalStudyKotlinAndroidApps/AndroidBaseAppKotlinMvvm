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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.di

import androidx.lifecycle.ViewModel
import co.androidbaseappkotlinmvvm.commons.ui.extensions.viewModel
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail.MovieDetailFragment
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail.MovieDetailViewModel
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail.di.MovieDetailModule
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail.model.MovieDetailMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieDetailModuleTest {

    @MockK
    lateinit var fragment: MovieDetailFragment
    lateinit var module: MovieDetailModule

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun initializeCharacterDetailModule_ShouldSetUpCorrectly() {
        module = MovieDetailModule(fragment)

        assertEquals(fragment, module.fragment)
    }

    @Test
    fun verifyProvidedCharacterDetailViewModel() {
        mockkStatic("co.androidbaseappkotlinmvvm.commons.ui.extensions.FragmentExtensionsKt")

        every {
            fragment.viewModel(any(), any<() -> ViewModel>())
        } returns mockk<MovieDetailViewModel>()

        val factoryCaptor = slot<() -> MovieDetailViewModel>()
        val MovieRepository = mockk<MovieRepository>(relaxed = true)
        val favoriteRepository = mockk<MovieFavoriteRepository>(relaxed = true)
        val mapper = mockk<MovieDetailMapper>(relaxed = true)
        module = MovieDetailModule(fragment)
        module.providesMovieDetailViewModel(
            movieRepository = MovieRepository,
            movieFavoriteRepository = favoriteRepository,
            movieDetailMapper = mapper
        )

        verify {
            fragment.viewModel(factory = capture(factoryCaptor))
        }

        factoryCaptor.captured().run {
            assertEquals(MovieRepository, this.movieRepository)
            assertEquals(favoriteRepository, this.movieFavoriteRepository)
            assertEquals(mapper, this.movieDetailMapper)
        }
    }
}
