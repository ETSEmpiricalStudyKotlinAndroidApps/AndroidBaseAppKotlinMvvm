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
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import co.androidbaseappkotlinmvvm.core.database.MovieDatabase
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.core.di.modules.DatabaseModule
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
        val context: Context = mock()
        val marvelDatabase = databaseModule.provideMarvelDatabase(context)

        assertNotNull(marvelDatabase.characterFavoriteDao())
    }

    @Test
    fun verifyProvidedCharacterFavoriteDao() {
        val movieDatabase: MovieDatabase = mock()
        val movieFavoriteDao: MovieFavoriteDao = mock()

        doReturn(movieFavoriteDao).whenever(movieDatabase).movieFavoriteDao()

        assertEquals(
            movieFavoriteDao,
            databaseModule.provideCharacterFavoriteDao(movieDatabase)
        )
        verify(movieDatabase).movieFavoriteDao()
    }

    @Test
    fun verifyProvidedCharacterFavoriteRepository() {
        val movieFavoriteDao: MovieFavoriteDao = mock()
        val repository = databaseModule.provideCharacterFavoriteRepository(movieFavoriteDao)

        assertEquals(movieFavoriteDao, repository.movieFavoriteDao)
    }
}
