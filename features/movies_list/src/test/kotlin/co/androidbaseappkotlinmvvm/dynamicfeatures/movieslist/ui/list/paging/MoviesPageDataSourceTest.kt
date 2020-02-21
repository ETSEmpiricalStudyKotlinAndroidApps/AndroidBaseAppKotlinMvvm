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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource.LoadCallback
import androidx.paging.PageKeyedDataSource.LoadInitialCallback
import androidx.paging.PageKeyedDataSource.LoadInitialParams
import androidx.paging.PageKeyedDataSource.LoadParams
import co.androidbaseappkotlinmvvm.core.network.NetworkState
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.ResultsResponse
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.model.MovieItem
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.model.MovieItemMapper
import co.androidbaseappkotlinmvvm.libraries.testutils.rules.CoroutineRule
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesPageDataSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    interface Callback : () -> Unit

    @MockK(relaxed = true)
    lateinit var repository: MovieRepository
    @MockK(relaxed = true)
    lateinit var mapper: MovieItemMapper
    @MockK(relaxed = true)
    lateinit var networkState: MutableLiveData<NetworkState>
    @MockK(relaxed = true)
    lateinit var retry: Callback

    @InjectMockKs(injectImmutable = true, overrideValues = true)
    lateinit var dataSource: MoviesPageDataSource

    private var scope = CoroutineScope(Dispatchers.Main)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun loadInitial_ShouldPostLoadingState() {
        val params = mockk<LoadInitialParams<Int>>()
        val callback = mockk<LoadInitialCallback<Int, MovieItem>>()
        dataSource.loadInitial(params, callback)

        verify { networkState.postValue(NetworkState.Loading()) }
    }

    @Test
    fun loadInitial_WithError_ShouldPostErrorState() {
        val params = mockk<LoadInitialParams<Int>>()
        val callback = mockk<LoadInitialCallback<Int, MovieItem>>()
        dataSource.loadInitial(params, callback)

        assertNotNull(retry)
        verify { networkState.postValue(NetworkState.Error()) }
    }

    @Test
    fun loadInitial_WithSuccessEmptyData_ShouldPostEmptySuccessState() {
        val params = LoadInitialParams<Int>(100, false)
        val callback = mockk<LoadInitialCallback<Int, MovieItem>>(relaxed = true)
        val emptyData = emptyList<MovieItem>()
        val response = mockk<ResultsResponse<MovieResponse>>()

        coEvery { mapper.map(any()) } returns emptyData
        coEvery { repository.getMovies(any()) } returns response

        dataSource.loadInitial(params, callback)

        coVerify { repository.getMovies(0) }
        coVerify { mapper.map(response) }
        verify { callback.onResult(emptyData, null, PAGE_INIT_ELEMENTS) }
        verify {
            networkState.postValue(
                NetworkState.Success(
                    isAdditional = false,
                    isEmptyResponse = true
                )
            )
        }
    }

    @Test
    fun loadInitial_WithSuccessData_ShouldPostNonEmptySuccessState() {
        val params = LoadInitialParams<Int>(0, true)
        val callback = mockk<LoadInitialCallback<Int, MovieItem>>(relaxed = true)
        val data = listOf(mockk<MovieItem>())
        val response = mockk<ResultsResponse<MovieResponse>>()

        coEvery { mapper.map(any()) } returns data
        coEvery { repository.getMovies(any()) } returns response

        dataSource.loadInitial(params, callback)

        coVerify { repository.getMovies(0) }
        coVerify { mapper.map(response) }
        verify { callback.onResult(data, null, PAGE_INIT_ELEMENTS) }
        verify { networkState.postValue(NetworkState.Success()) }
    }

    @Test
    fun loadAfter_ShouldPostAdditionalLoadingState() {
        val params = mockk<LoadParams<Int>>()
        val callback = mockk<LoadCallback<Int, MovieItem>>()
        dataSource.loadAfter(params, callback)

        verify { networkState.postValue(NetworkState.Loading(true)) }
    }

    @Test
    fun loadAfter_WithError_ShouldPostAdditionalErrorState() {
        val params = LoadParams(0, 0)
        val callback = mockk<LoadCallback<Int, MovieItem>>()
        dataSource.loadAfter(params, callback)

        assertNotNull(retry)
        verify { networkState.postValue(NetworkState.Error(true)) }
    }

    @Test
    fun loadAfter_WithSuccessEmptyData_ShouldPostAdditionalEmptySuccessState() {
        val paramKey = 100
        val params = LoadParams(paramKey, 0)
        val callback = mockk<LoadCallback<Int, MovieItem>>(relaxed = true)
        val emptyData = emptyList<MovieItem>()
        val response = mockk<ResultsResponse<MovieResponse>>()

        coEvery { mapper.map(any()) } returns emptyData
        coEvery { repository.getMovies(any()) } returns response

        dataSource.loadAfter(params, callback)

        coVerify { repository.getMovies(PAGE_INIT_ELEMENTS) }
        coVerify { mapper.map(response) }
        verify { callback.onResult(emptyData, paramKey + PAGE_INIT_ELEMENTS) }
        verify {
            networkState.postValue(
                NetworkState.Success(
                    isAdditional = true,
                    isEmptyResponse = true
                )
            )
        }
    }

    @Test
    fun loadAfter_WithSuccessData_ShouldPostAdditionalNonEmptySuccessState() {
        val paramKey = 0
        val params = LoadParams(paramKey, 0)
        val callback = mockk<LoadCallback<Int, MovieItem>>(relaxed = true)
        val data = listOf(mockk<MovieItem>())
        val response = mockk<ResultsResponse<MovieResponse>>()

        coEvery { mapper.map(any()) } returns data
        coEvery { repository.getMovies(any()) } returns response

        dataSource.loadAfter(params, callback)

        coVerify { repository.getMovies(paramKey) }
        coVerify { mapper.map(response) }
        verify { callback.onResult(data, paramKey + PAGE_INIT_ELEMENTS) }
        verify {
            networkState.postValue(
                NetworkState.Success(
                    isAdditional = true,
                    isEmptyResponse = false
                )
            )
        }
    }

    @Test
    fun loadBefore_ShouldDoNothing() {
        val params = mockk<LoadParams<Int>>()
        val callback = mockk<LoadCallback<Int, MovieItem>>()
        dataSource.loadBefore(params, callback)

        verify { params wasNot Called }
        verify { callback wasNot Called }
    }
}
