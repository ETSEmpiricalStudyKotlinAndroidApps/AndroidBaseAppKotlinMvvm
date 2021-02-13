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
import co.androidbaseappkotlinmvvm.core.BuildConfig
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.core.network.services.MovieService
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val API_PUBLIC_KEY = BuildConfig.MOVIE_API_KEY

class MovieRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var movieService: MovieService
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepository = MovieRepository(movieService)
    }

    @Test
    fun getMovies() = runBlocking {
        val pageRequest = 1
        val apiKey = slot<String>()
        val page = slot<Int>()

        movieRepository.getMovies(
            page = pageRequest
        )

        coVerify {
            movieService.getMovies(apiKey = capture(apiKey), page = capture(page))
        }

        assertEquals(API_PUBLIC_KEY, apiKey.captured)
        assertNotNull(page.captured)
    }

    @Test
    fun getMovie() = runBlocking {
        val movieId = 3L
        val id = slot<Long>()
        val apiKey = slot<String>()

        movieRepository.getMovie(movieId)

        coVerify {
            movieService.getMovie(id = capture(id), apiKey = capture(apiKey))
        }

        assertEquals(movieId, id.captured)
        assertEquals(API_PUBLIC_KEY, apiKey.captured)
    }
}
