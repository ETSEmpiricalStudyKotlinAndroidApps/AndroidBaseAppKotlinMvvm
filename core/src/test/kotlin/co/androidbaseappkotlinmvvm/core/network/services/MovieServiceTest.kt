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
import co.androidbaseappkotlinmvvm.core.network.responses.BaseResponse
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import kotlin.math.roundToInt
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
        const val STATUS_204 = "mock-responses/get-movies-status204.json"
        const val STATUS_401 = "mock-responses/get-movies-status401.json"
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
            "/movie/popular?apikey=$apiKey&page=$page",
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

        assertCodeStatus(200, response.code)
        assertEquals("Ok", response.status)
        assertNull(response.message)

        val responseData = response.data
        assertEquals(1492, responseData.total)
        assertEquals(100, responseData.results.size)
        assertThat(responseData.results, instanceOf(List::class.java))
    }

    @Test
    fun responseGetMovies_StatusCode204() = runBlocking {
        enqueueResponse(MockResponses.GetMovies.STATUS_204)
        val page = 1
        val response = service.getMovies(
            page = page
        )

        assertCodeStatus(204, response.code)
        assertEquals("Empty", response.status)
        assertNull(response.message)

        val responseData = response.data
        assertEquals(0, responseData.count)
        assertEquals(1493, responseData.total)
        assertEquals(0, responseData.results.size)
    }

    @Test
    fun responseGetMovies_StatusCode401() = runBlocking {
        enqueueResponse(MockResponses.GetMovies.STATUS_401)
        val response = service.getMovies()

        assertEquals("InvalidCredentials", response.code)
        assertEquals("That hash, timestamp and key combination is invalid.", response.message)
        assertNull(response.status)
        assertNull(response.data)
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
            "/movie/$id?apikey=$apiKey", request.path
        )
    }

    @Test
    fun responseMovieId_StatusCode200() {
        runBlocking {
            enqueueResponse(MockResponses.GetMovieId.STATUS_200)
            val movieId = 1011334L
            val response = service.getMovie(movieId)

            assertCodeStatus(200, response.code)
            assertEquals("Ok", response.status)
            assertNull(response.message)

            response.data.run {
                assertEquals(1, count)
                assertEquals(1, total)
                assertEquals(1, results.size)
            }

            response.data.results.first().run {
                assertEquals(movieId, id)
                assertEquals("3-D Man", name)
                assertEquals("", description)

                assertEquals(
                    "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg",
                    thumbnail.path
                )
            }
        }
    }

    @Test
    fun responseMovieId_StatusCode401() = runBlocking {
        enqueueResponse(MockResponses.GetMovieId.STATUS_401)
        val response = service.getMovie()

        assertEquals("InvalidCredentials", response.code)
        assertEquals("That hash, timestamp and key combination is invalid.", response.message)
        assertNull(response.status)
        assertNull(response.data)
    }

    @Test
    fun responseMovieId_StatusCode404() = runBlocking {
        enqueueResponse(MockResponses.GetMovieId.STATUS_404)
        val response = service.getMovie()

        assertCodeStatus(404, response.code)
        assertEquals("We couldn't find that movie", response.status)
        assertNull(response.message)
        assertNull(response.data)
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

    private fun assertCodeStatus(number1: Number, number2: Any) {
        assertEquals(number1, (number2 as Double).roundToInt())
    }

    private suspend fun MovieService.getMovie(
        id: Long = 0L
    ): BaseResponse<MovieResponse> {
        return service.getMovie(
            id = id,
            apiKey = ""
        )
    }

    private suspend fun MovieService.getMovies(
        apiKey: String = "",
        page: Int = 1
    ): BaseResponse<MovieResponse> {
        return service.getMovies(
            apiKey = apiKey,
            page = page
        )
    }
}
