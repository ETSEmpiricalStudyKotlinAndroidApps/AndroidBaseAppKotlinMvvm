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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import co.androidbaseappkotlinmvvm.core.database.MovieDatabase
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.libraries.testutils.livedata.getValue
import co.androidbaseappkotlinmvvm.libraries.testutils.robolectric.TestRobolectric
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieFavoriteDaoTest : TestRobolectric() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var movieFavoriteDao: MovieFavoriteDao
    private val fakeMoviesFavorite = listOf(
        MovieFavorite(0, "3-D Man", "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg"),
        MovieFavorite(1, "A-Bomb (HAS)", "http://i.annihil.us/5232158de5b16.jpg"),
        MovieFavorite(2, "A.I.M", "http://i.annihil.us/52602f21f29ec.jpg")
    )

    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room
            .inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        movieFavoriteDao = database.movieFavoriteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun obtainAllMoviesFavoriteLiveData_WithoutData_ShouldReturnNull() {
        val movies = movieFavoriteDao.getAllMoviesFavoriteLiveData()
        assertTrue(getValue(movies).isNullOrEmpty())
    }

    @Test
    fun obtainAllMoviesFavoriteLiveData_WithData_ShouldReturnSorted() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)
        val movies = movieFavoriteDao.getAllMoviesFavoriteLiveData()

        assertEquals(fakeMoviesFavorite, getValue(movies))
    }

    @Test
    fun obtainAllMoviesFavorite_WithoutData_ShouldReturnEmpty() = runBlocking {
        assertTrue(movieFavoriteDao.getAllMoviesFavorite().isNullOrEmpty())
    }

    @Test
    fun obtainAllMoviesFavorite_WithData_ShouldReturnSorted() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)

        assertEquals(fakeMoviesFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }

    @Test
    fun obtainMovieFavoriteById_WithoutData_ShouldNotFound() = runBlocking {
        val movieToFind = fakeMoviesFavorite.first()

        assertNull(movieFavoriteDao.getMovieFavorite(movieToFind.id))
    }

    @Test
    fun obtainMovieFavoriteById_WithData_ShouldFound() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)

        val movieToFind = fakeMoviesFavorite.first()
        assertEquals(movieToFind, movieFavoriteDao.getMovieFavorite(movieToFind.id))
    }

    @Test
    fun insertMovieFavorite_ShouldAdd() = runBlocking {
        fakeMoviesFavorite.forEach {
            movieFavoriteDao.insertMovieFavorite(it)
        }

        assertEquals(fakeMoviesFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }

    @Test
    fun deleteAllMoviesFavorite_ShouldRemoveAll() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)
        movieFavoriteDao.deleteAllMoviesFavorite()

        assertTrue(movieFavoriteDao.getAllMoviesFavorite().isNullOrEmpty())
    }

    @Test
    fun deleteMovieFavorite_Stored_ShouldRemoveIt() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)

        val movieToRemove = fakeMoviesFavorite.first()
        movieFavoriteDao.deleteMovieFavorite(movieToRemove)

        assertThat(movieFavoriteDao.getAllMoviesFavorite(), not(hasItem(movieToRemove)))
    }

    @Test
    fun deleteMovieFavorite_NoStored_ShouldNotRemoveNothing() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)

        val movieToRemove = MovieFavorite(5, "test", "url")
        movieFavoriteDao.deleteMovieFavorite(movieToRemove)

        assertEquals(fakeMoviesFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }

    @Test
    fun deleteMovieFavoriteById_Stored_ShouldRemoveIt() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)

        val movieToRemove = fakeMoviesFavorite.first()
        movieFavoriteDao.deleteMovieFavoriteById(movieToRemove.id)

        assertThat(movieFavoriteDao.getAllMoviesFavorite(), not(hasItem(movieToRemove)))
    }

    @Test
    fun deleteMovieFavoriteById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeMoviesFavorite)

        val movieNoStoredId = 100L
        movieFavoriteDao.deleteMovieFavoriteById(movieNoStoredId)

        assertEquals(fakeMoviesFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }
}
