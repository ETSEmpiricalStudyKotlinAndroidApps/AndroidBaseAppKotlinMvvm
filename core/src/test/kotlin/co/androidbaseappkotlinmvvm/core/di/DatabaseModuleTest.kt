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

package co.androidbaseappkotlinmvvm.core.di

import android.content.Context
import co.androidbaseappkotlinmvvm.core.database.MovieDatabase
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.core.di.modules.DatabaseModule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DatabaseModuleTest {

    private lateinit var databaseModule: DatabaseModule

    @Before
    fun setUp() {
        databaseModule = DatabaseModule()
    }

    @Test
    fun verifyProvidedMarvelDatabase() {
        val context: Context = mockk()
        val marvelDatabase = databaseModule.provideMovieDatabase(context)

        assertNotNull(marvelDatabase.movieFavoriteDao())
    }

    @Test
    fun verifyProvidedMovieFavoriteDao() {
        val movieDatabase: MovieDatabase = mockk()
        val movieFavoriteDao: MovieFavoriteDao = mockk()

        every { movieDatabase.movieFavoriteDao() } returns movieFavoriteDao

        assertEquals(
            movieFavoriteDao,
            databaseModule.provideMovieFavoriteDao(movieDatabase)
        )
        verify { movieDatabase.movieFavoriteDao() }
    }

    @Test
    fun verifyProvidedMovieFavoriteRepository() {
        val movieFavoriteDao: MovieFavoriteDao = mockk()
        val repository = databaseModule.provideMovieFavoriteRepository(movieFavoriteDao)

        assertEquals(movieFavoriteDao, repository.movieFavoriteDao)
    }
}
