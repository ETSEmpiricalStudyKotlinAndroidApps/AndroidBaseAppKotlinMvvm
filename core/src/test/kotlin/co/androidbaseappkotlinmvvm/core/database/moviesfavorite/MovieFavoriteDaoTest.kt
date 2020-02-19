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

package co.androidbaseappkotlinmvvm.core.database.charactersfavorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import co.androidbaseappkotlinmvvm.core.database.MovieDatabase
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.libraries.testutils.livedata.getValue
import co.androidbaseappkotlinmvvm.libraries.testutils.robolectric.TestRobolectric
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.not
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
    private val fakeCharactersFavorite = listOf(
        MovieFavorite(0, "3-D Man", "http://i.annihil.us/535fecbbb9784.jpg"),
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
    fun obtainAllCharactersFavoriteLiveData_WithoutData_ShouldReturnNull() {
        val characters = movieFavoriteDao.getAllMoviesFavoriteLiveData()
        assertTrue(getValue(characters).isNullOrEmpty())
    }

    @Test
    fun obtainAllCharactersFavoriteLiveData_WithData_ShouldReturnSorted() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)
        val characters = movieFavoriteDao.getAllMoviesFavoriteLiveData()

        assertEquals(fakeCharactersFavorite, getValue(characters))
    }

    @Test
    fun obtainAllCharactersFavorite_WithoutData_ShouldReturnEmpty() = runBlocking {
        assertTrue(movieFavoriteDao.getAllMoviesFavorite().isNullOrEmpty())
    }

    @Test
    fun obtainAllCharactersFavorite_WithData_ShouldReturnSorted() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)

        assertEquals(fakeCharactersFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }

    @Test
    fun obtainCharacterFavoriteById_WithoutData_ShouldNotFound() = runBlocking {
        val characterToFind = fakeCharactersFavorite.first()

        assertNull(movieFavoriteDao.getMovieFavorite(characterToFind.id))
    }

    @Test
    fun obtainCharacterFavoriteById_WithData_ShouldFound() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)

        val characterToFind = fakeCharactersFavorite.first()
        assertEquals(characterToFind, movieFavoriteDao.getMovieFavorite(characterToFind.id))
    }

    @Test
    fun insertCharacterFavorite_ShouldAdd() = runBlocking {
        fakeCharactersFavorite.forEach {
            movieFavoriteDao.insertMovieFavorite(it)
        }

        assertEquals(fakeCharactersFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }

    @Test
    fun deleteAllCharactersFavorite_ShouldRemoveAll() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)
        movieFavoriteDao.deleteAllMoviesFavorite()

        assertTrue(movieFavoriteDao.getAllMoviesFavorite().isNullOrEmpty())
    }

    @Test
    fun deleteCharacterFavorite_Stored_ShouldRemoveIt() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)

        val characterToRemove = fakeCharactersFavorite.first()
        movieFavoriteDao.deleteMovieFavorite(characterToRemove)

        assertThat(movieFavoriteDao.getAllMoviesFavorite(), not(hasItem(characterToRemove)))
    }

    @Test
    fun deleteCharacterFavorite_NoStored_ShouldNotRemoveNothing() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)

        val characterToRemove = MovieFavorite(5, "test", "url")
        movieFavoriteDao.deleteMovieFavorite(characterToRemove)

        assertEquals(fakeCharactersFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }

    @Test
    fun deleteCharacterFavoriteById_Stored_ShouldRemoveIt() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)

        val characterToRemove = fakeCharactersFavorite.first()
        movieFavoriteDao.deleteMovieFavoriteById(characterToRemove.id)

        assertThat(movieFavoriteDao.getAllMoviesFavorite(), not(hasItem(characterToRemove)))
    }

    @Test
    fun deleteCharacterFavoriteById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        movieFavoriteDao.insertMoviesFavorites(fakeCharactersFavorite)

        val characterNoStoredId = 100L
        movieFavoriteDao.deleteMovieFavoriteById(characterNoStoredId)

        assertEquals(fakeCharactersFavorite, movieFavoriteDao.getAllMoviesFavorite())
    }
}
