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

package co.androidbaseappkotlinmvvm.favorite.caractersfavorites.ui.favorite.di

import androidx.lifecycle.ViewModel
import co.androidbaseappkotlinmvvm.commons.ui.extensions.viewModel
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.MoviesFavoriteFragment
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.MoviesFavoriteViewModel
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.di.MoviesFavoriteModule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MoviesFavoriteModuleTest {

    @MockK
    lateinit var fragment: MoviesFavoriteFragment
    lateinit var module: MoviesFavoriteModule

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun initializeCharactersFavoriteModule_ShouldSetUpCorrectly() {
        module = MoviesFavoriteModule(fragment)

        assertEquals(fragment, module.fragment)
    }

    @Test
    fun verifyProvidedCharactersFavoriteViewModel() {
        mockkStatic("co.androidbaseappkotlinmvvm.commons.ui.extensions.FragmentExtensionsKt")

        every {
            fragment.viewModel(any(), any<() -> ViewModel>())
        } returns mockk<MoviesFavoriteViewModel>()

        val factoryCaptor = slot<() -> MoviesFavoriteViewModel>()
        val favoriteRepository = mockk<MovieFavoriteRepository>(relaxed = true)
        module = MoviesFavoriteModule(fragment)
        module.providesCharactersFavoriteViewModel(favoriteRepository)

        verify {
            fragment.viewModel(factory = capture(factoryCaptor))
        }

        assertEquals(favoriteRepository, factoryCaptor.captured().movieFavoriteRepository)
    }

    @Test
    fun verifyProvidedCharactersFavoriteAdapter() {
        module = MoviesFavoriteModule(fragment)

        assertNotNull(module.providesCharactersFavoriteAdapter())
    }
}
