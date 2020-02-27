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

package co.androidbaseappkotlinmvvm.core.network.services

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.ResultsResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MockResponses {
    object GetMovies {
        const val STATUS_200 = "mock-responses/get-movies-status200.json"
        const val STATUS_401 = "mock-responses/get-movies-status401.json"
        const val STATUS_404 = "mock-responses/get-movies-status404.json"
    }

    object GetMovieId {
        const val STATUS_200 = "mock-responses/get-movie-id-status200.json"
        const val STATUS_401 = "mock-responses/get-movie-id-status401.json"
        const val STATUS_404 = "mock-responses/get-movie-id-status404.json"
    }
}

class MovieServiceTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var service: MovieService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestGetMovies() = runBlocking {
        enqueueResponse(MockResponses.GetMovies.STATUS_200)
        val apiKey = "mockApiKey"
        val page = 1
        service.getMovies(
            apiKey = apiKey,
            page = page
        )

        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals(
            "/movie/popular?api_key=$apiKey&page=$page",
            request.path
        )
    }

    @Test
    fun responseGetMovies_StatusCode200() = runBlocking {
        enqueueResponse(MockResponses.GetMovies.STATUS_200)
        val page = 1
        val response = service.getMovies(
            apiKey = "",
            page = page
        )

        assertNull(response.code)
        assertNull(response.message)

        val responseData = response.results
        assertEquals(19629, response.total)
        assertEquals(20, responseData.size)
        assertThat(responseData, instanceOf(List::class.java))
    }

    @Test
    fun responseGetMovies_StatusCode404() = runBlocking {
        enqueueResponse(MockResponses.GetMovies.STATUS_404)
        val page = 1
        val response = service.getMovies(
            page = page
        )

        assertEquals(34, response.code)
        assertEquals("The resource you requested could not be found.", response.message)
        assertNull(response.results)
    }

    @Test
    fun responseGetMovies_StatusCode401() = runBlocking {
        enqueueResponse(MockResponses.GetMovies.STATUS_401)
        val response = service.getMovies()

        assertEquals(7, response.code)
        assertEquals("Invalid API key: You must be granted a valid key.", response.message)
        assertNull(response.results)
    }

    @Test
    fun requestMovieId() = runBlocking {
        enqueueResponse(MockResponses.GetMovieId.STATUS_200)
        val id = 1L
        val apiKey = "MockApiKey"
        service.getMovie(
            id = id,
            apiKey = apiKey
        )

        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals(
            "/movie/$id?api_key=$apiKey", request.path
        )
    }

    @Test
    fun responseMovieId_StatusCode200() {
        runBlocking {
            enqueueResponse(MockResponses.GetMovieId.STATUS_200)
            val movieId = 550L
            val response = service.getMovie(movieId)

            assertNull(response.code)
            assertNull(response.message)

            assertEquals(movieId, response.id)
            assertEquals("Fight Club", response.name)
            assertEquals("A ticking-time-bomb insomniac and a slippery soap salesman channel " +
                    "primal male aggression into a shocking new form of therapy. " +
                    "Their concept catches on, with underground \"fight clubs\" " +
                    "forming in every town, until an eccentric gets in the way " +
                    "and ignites an out-of-control spiral toward oblivion.", response.description)

            assertNull(response.image)
        }
    }

    @Test
    fun responseMovieId_StatusCode401() = runBlocking {
        enqueueResponse(MockResponses.GetMovieId.STATUS_401)
        val response = service.getMovie()

        assertEquals(7, response.code)
        assertEquals("Invalid API key: You must be granted a valid key.", response.message)
        assertEquals(0, response.id)
        assertEquals(null, response.name)
        assertEquals(null, response.description)
        assertEquals(null, response.image)
    }

    @Test
    fun responseMovieId_StatusCode404() = runBlocking {
        enqueueResponse(MockResponses.GetMovieId.STATUS_404)
        val response = service.getMovie()

        assertEquals(34, response.code)
        assertEquals("The resource you requested could not be found.", response.message)
    }

    private fun enqueueResponse(filePath: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
        val bufferSource = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse.setBody(
                bufferSource!!.readString(Charsets.UTF_8)
            )
        )
    }

    private suspend fun MovieService.getMovie(
        id: Long = 0L
    ): MovieResponse {
        return service.getMovie(
            id = id,
            apiKey = ""
        )
    }

    private suspend fun MovieService.getMovies(
        apiKey: String = "",
        page: Int = 1
    ): ResultsResponse<MovieResponse> {
        return service.getMovies(
            apiKey = apiKey,
            page = page
        )
    }
}
