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

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieFavoriteRepositoryTest {

    @Mock
    lateinit var movieFavoriteDao: MovieFavoriteDao
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieFavoriteRepository = MovieFavoriteRepository(movieFavoriteDao)
    }

    @Test
    fun getAllCharactersFavoriteLiveData_ShouldInvokeCorrectDaoMethod() {
        movieFavoriteRepository.getAllMoviesFavoriteLiveData()

        verify(movieFavoriteDao).getAllMoviesFavoriteLiveData()
    }

    @Test
    fun getAllCharactersFavorite_ShouldInvokeCorrectDaoMethod() {
        runBlocking {
            movieFavoriteRepository.getAllMoviesFavorite()

            verify(movieFavoriteDao).getAllMoviesFavorite()
        }
    }

    @Test
    fun getCharacterFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val characterIdToFind = 1L
        val characterIdCaptor = argumentCaptor<Long>()
        movieFavoriteRepository.getCharacterFavorite(characterIdToFind)

        verify(movieFavoriteDao).getMovieFavorite(characterIdCaptor.capture())
        assertEquals(characterIdToFind, characterIdCaptor.lastValue)
    }

    @Test
    fun deleteAllCharactersFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        movieFavoriteRepository.deleteAllCharactersFavorite()

        verify(movieFavoriteDao).deleteAllMoviesFavorite()
    }

    @Test
    fun deleteCharacterFavoriteById_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val characterIdToDelete = 1L
        val characterIdCaptor = argumentCaptor<Long>()
        movieFavoriteRepository.deleteCharacterFavoriteById(characterIdToDelete)

        verify(movieFavoriteDao).deleteMovieFavoriteById(characterIdCaptor.capture())
        assertEquals(characterIdToDelete, characterIdCaptor.lastValue)
    }

    @Test
    fun deleteCharacterFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val characterToDelete = MovieFavorite(
            0,
            "A.I.M",
            "http://i.annihil.us/535fecbbb9784.jpg"
        )
        val characterFavoriteCaptor = argumentCaptor<MovieFavorite>()
        movieFavoriteRepository.deleteCharacterFavorite(characterToDelete)

        verify(movieFavoriteDao).deleteMovieFavorite(characterFavoriteCaptor.capture())
        assertEquals(characterToDelete, characterFavoriteCaptor.lastValue)
    }

    @Test
    fun insertCharactersFavorites_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val charactersToInsert = listOf(
            MovieFavorite(0, "3-D Man", "http://i.annihil.us/535fecbbb9784.jpg"),
            MovieFavorite(1, "A-Bomb (HAS)", "http://i.annihil.us/5232158de5b16.jpg"),
            MovieFavorite(2, "A.I.M", "http://i.annihil.us/52602f21f29ec.jpg")
        )
        val charactersInsertedCaptor = argumentCaptor<List<MovieFavorite>>()
        movieFavoriteRepository.insertCharactersFavorites(charactersToInsert)

        verify(movieFavoriteDao).insertMoviesFavorites(charactersInsertedCaptor.capture())
        assertEquals(charactersToInsert, charactersInsertedCaptor.lastValue)
    }

    @Test
    fun insertCharacterFavorite_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val characterToInsert = MovieFavorite(
            0,
            "A.I.M",
            "http://i.annihil.us/535fecbbb9784.jpg"
        )
        val characterInsertedCaptor = argumentCaptor<MovieFavorite>()
        movieFavoriteRepository.insertCharacterFavorite(
            id = characterToInsert.id,
            name = characterToInsert.name,
            imageUrl = characterToInsert.imageUrl
        )

        verify(movieFavoriteDao).insertMovieFavorite(characterInsertedCaptor.capture())
        assertEquals(characterToInsert, characterInsertedCaptor.lastValue)
    }
}
