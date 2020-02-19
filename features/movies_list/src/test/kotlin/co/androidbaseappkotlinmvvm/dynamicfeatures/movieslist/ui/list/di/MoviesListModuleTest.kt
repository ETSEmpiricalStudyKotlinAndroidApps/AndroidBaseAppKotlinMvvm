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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.androidbaseappkotlinmvvm.commons.ui.extensions.viewModel
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.MoviesListFragment
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.MoviesListViewModel
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.model.MovieItemMapper
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.paging.MoviesPageDataSourceFactory
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.di.MoviesListModule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesListModuleTest {

    @MockK
    lateinit var fragment: MoviesListFragment
    lateinit var module: MoviesListModule

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun initializeMoviesListModule_ShouldSetUpCorrectly() {
        module = MoviesListModule(fragment)

        assertEquals(fragment, module.fragment)
    }

    @Test
    fun verifyProvidedMoviesListViewModel() {
        mockkStatic("co.androidbaseappkotlinmvvm.commons.ui.extensions.FragmentExtensionsKt")

        every {
            fragment.viewModel(any(), any<() -> ViewModel>())
        } returns mockk<MoviesListViewModel>()

        val factoryCaptor = slot<() -> MoviesListViewModel>()
        val dataFactory = mockk<MoviesPageDataSourceFactory>(relaxed = true)
        module = MoviesListModule(fragment)
        module.providesMoviesListViewModel(dataFactory)

        verify {
            fragment.viewModel(factory = capture(factoryCaptor))
        }

        assertEquals(dataFactory, factoryCaptor.captured().dataSourceFactory)
    }

    @Test
    fun verifyProvidedMoviesPageDataSource() {
        val repository = mockk<MovieRepository>(relaxed = true)
        val mapper = mockk<MovieItemMapper>(relaxed = true)
        val viewModel = mockk<MoviesListViewModel>(relaxed = true)
        val scope = mockk<CoroutineScope>()
        every { viewModel.viewModelScope } returns scope

        module = MoviesListModule(fragment)
        val dataSource = module.providesMoviesPageDataSource(
            viewModel = viewModel,
            repository = repository,
            mapper = mapper
        )

        assertEquals(repository, dataSource.repository)
        assertEquals(mapper, dataSource.mapper)
        assertEquals(scope, dataSource.scope)
    }
}
