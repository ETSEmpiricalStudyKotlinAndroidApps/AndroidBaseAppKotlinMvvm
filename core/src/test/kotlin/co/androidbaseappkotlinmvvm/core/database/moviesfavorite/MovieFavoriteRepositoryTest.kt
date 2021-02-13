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

package co.androidbaseappkotlinmvvm.core.database.moviesfavorite

import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieFavoriteRepositoryTest {

    @MockK(relaxed = true)
    lateinit var movieFavoriteDao: MovieFavoriteDao
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieFavoriteRepository = MovieFavoriteRepository(movieFavoriteDao)
    }

    @Test
    fun getAllMoviesFavoriteLiveData_ShouldInvokeCorrectDaoMethod() {
        movieFavoriteRepository.getAllMoviesFavoriteLiveData()

        verify{ movieFavoriteDao.getAllMoviesFavoriteLiveData() }
    }

    @Test
    fun getAllMoviesFavorite_ShouldInvokeCorrectDaoMethod() {
        runBlocking {
            movieFavoriteRepository.getAllMoviesFavorite()

            coVerify { movieFavoriteDao.getAllMoviesFavorite() }
        }
    }

    @Test
    fun getMovieFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val movieIdToFind = 1L
        val movieIdCaptor = slot<Long>()
        movieFavoriteRepository.getMovieFavorite(movieIdToFind)

        coVerify {  movieFavoriteDao.getMovieFavorite(capture(movieIdCaptor)) }
        assertEquals(movieIdToFind, movieIdCaptor.captured)
    }

    @Test
    fun deleteAllMoviesFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        movieFavoriteRepository.deleteAllMoviesFavorite()

        coVerify { movieFavoriteDao.deleteAllMoviesFavorite() }
    }

    @Test
    fun deleteMovieFavoriteById_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val movieIdToDelete = 1L
        val movieIdCaptor = slot<Long>()
        movieFavoriteRepository.deleteMovieFavoriteById(movieIdToDelete)

        coVerify { movieFavoriteDao.deleteMovieFavoriteById(capture(movieIdCaptor)) }
        assertEquals(movieIdToDelete, movieIdCaptor.captured)
    }

    @Test
    fun deleteMovieFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val movieToDelete = MovieFavorite(
            0,
            "A.I.M",
            "/vOipe2myi26UDwP978hsYOrnUWC.jpg"
        )
        val movieFavoriteCaptor = slot<MovieFavorite>()
        movieFavoriteRepository.deleteMovieFavorite(movieToDelete)

        coVerify { movieFavoriteDao.deleteMovieFavorite(capture(movieFavoriteCaptor)) }
        assertEquals(movieToDelete, movieFavoriteCaptor.captured)
    }

    @Test
    fun insertMoviesFavorites_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val moviesToInsert = listOf(
            MovieFavorite(0, "3-D Man", "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg"),
            MovieFavorite(1, "A-Bomb (HAS)", "/hU0E130tsGdsYa4K9lc3Xrn5Wyt.jpg"),
            MovieFavorite(2, "A.I.M", "/vOipe2myi26UDwP978hsYOrnUWC.jpg")
        )
        val moviesInsertedCaptor = slot<List<MovieFavorite>>()
        movieFavoriteRepository.insertMoviesFavorites(moviesToInsert)

        coVerify { movieFavoriteDao.insertMoviesFavorites(capture(moviesInsertedCaptor)) }
        assertEquals(moviesToInsert, moviesInsertedCaptor.captured)
    }

    @Test
    fun insertMovieFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val movieToInsert = MovieFavorite(
            0,
            "A.I.M",
            "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg"
        )
        val movieInsertedCaptor = slot<MovieFavorite>()
        movieFavoriteRepository.insertMovieFavorite(
            id = movieToInsert.id,
            name = movieToInsert.name,
            imageUrl = movieToInsert.imageUrl
        )

        coVerify { movieFavoriteDao.insertMovieFavorite(capture(movieInsertedCaptor)) }
        assertEquals(movieToInsert, movieInsertedCaptor.captured)
    }
}
