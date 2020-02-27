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

package co.androidbaseappkotlinmvvm.core.network.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import co.androidbaseappkotlinmvvm.core.BuildConfig
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.core.network.services.MovieService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val API_PUBLIC_KEY = BuildConfig.MOVIE_API_KEY

class MovieRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieService: MovieService
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository(movieService)
    }

    @Test
    fun getMovies() = runBlocking {
        val pageRequest = 1
        val (apiKey, page) =
            argumentCaptor<String, Int>()

        movieRepository.getMovies(
            page = pageRequest
        )

        verify(movieService).getMovies(
            apiKey = apiKey.capture(),
            page = page.capture()
        )

        assertEquals(API_PUBLIC_KEY, apiKey.lastValue)
        assertNotNull(page.lastValue)
    }

    @Test
    fun getMovie() = runBlocking {
        val movieId = 3L
        val (id, apiKey) = argumentCaptor<Long, String>()

        movieRepository.getMovie(movieId)

        verify(movieService).getMovie(
            id = id.capture(),
            apiKey = apiKey.capture()
        )

        assertEquals(movieId, id.lastValue)
        assertEquals(API_PUBLIC_KEY, apiKey.lastValue)
    }
}
