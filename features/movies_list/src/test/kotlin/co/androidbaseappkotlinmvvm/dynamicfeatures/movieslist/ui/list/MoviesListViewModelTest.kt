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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.androidbaseappkotlinmvvm.core.network.NetworkState
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.paging.MoviesPageDataSource
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.paging.MoviesPageDataSourceFactory
import co.androidbaseappkotlinmvvm.libraries.testutils.rules.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var dataSourceFactory: MoviesPageDataSourceFactory
    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<MoviesListViewState>
    @MockK(relaxed = true)
    lateinit var eventObserver: Observer<MoviesListViewEvent>
    lateinit var viewModel: MoviesListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun networkSuccessEmptyMovies_ShouldBeEmptyState() {
        val networkState = NetworkState.Success(
            isEmptyResponse = true
        )
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.Empty
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkSuccessAdditionalEmptyMovies_ShouldBeNoMoreElementsState() {
        val networkState = NetworkState.Success(
            isAdditional = true,
            isEmptyResponse = true
        )
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.NoMoreElements
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkSuccessAdditionalMovies_ShouldBeLoadedState() {
        val networkState = NetworkState.Success(
            isAdditional = true
        )
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.Loaded
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkSuccessMovies_ShouldBeLoadedState() {
        val networkState = NetworkState.Success()
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.Loaded
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkLoadingMovies_ShouldBeLoadingState() {
        val networkState = NetworkState.Loading()
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.Loading
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkAdditionalLoadingMovies_ShouldBeAddLoadingState() {
        val networkState = NetworkState.Loading(
            isAdditional = true
        )
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.AddLoading
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkErrorMovies_ShouldBeErrorState() {
        val networkState = NetworkState.Error()
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.Error
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkAdditionalErrorMovies_ShouldBeAddErrorState() {
        val networkState = NetworkState.Error(true)
        val fakePageDataSource = FakeMoviesPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<MoviesPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.state.observeForever(stateObserver)

        val expectedState = MoviesListViewState.AddError
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun refreshMovieList_ShouldInvokeDataSourceMethod() {
        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.refreshLoadedMoviesList()

        verify { dataSourceFactory.refresh() }
        verify(exactly = 0) { dataSourceFactory.retry() }
    }

    @Test
    fun retryMovieList_ShouldInvokeDataSourceMethod() {
        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.retryAddMoviesList()

        verify { dataSourceFactory.retry() }
        verify(exactly = 0) { dataSourceFactory.refresh() }
    }

    @Test
    fun openMovieDetail_ShouldSendAsEvent() {
        viewModel = MoviesListViewModel(dataSourceFactory = dataSourceFactory)
        viewModel.event.observeForever(eventObserver)

        val movieId = 1L
        viewModel.openMovieDetail(movieId)

        val expectedEvent = MoviesListViewEvent.OpenMovieDetail(movieId)
        assertEquals(expectedEvent, viewModel.event.value)
        verify { eventObserver.onChanged(expectedEvent) }
    }

    inner class FakeMoviesPageDataSource(
        forceNetworkState: NetworkState
    ) : MoviesPageDataSource(
        repository = mockk(),
        scope = mockk(),
        mapper = mockk()
    ) {
        init {
            networkState.postValue(forceNetworkState)
        }
    }
}
