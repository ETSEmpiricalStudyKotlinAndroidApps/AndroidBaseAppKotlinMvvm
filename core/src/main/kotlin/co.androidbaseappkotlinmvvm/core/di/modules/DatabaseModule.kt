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

package co.androidbaseappkotlinmvvm.core.di.modules

import android.content.Context
import androidx.room.Room
import co.androidbaseappkotlinmvvm.core.BuildConfig
import co.androidbaseappkotlinmvvm.core.database.MovieDatabase
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.core.database.migrations.MIGRATION_1_2
import co.androidbaseappkotlinmvvm.core.di.CoreComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class DatabaseModule {

    /**
     * Create a provider method binding for [MovieDatabase].
     *
     * @return Instance of movie database.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideMovieDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            BuildConfig.MOVIE_DATABASE_NAME
        ).addMigrations(MIGRATION_1_2)
            .build()

    /**
     * Create a provider method binding for [MovieFavoriteDao].
     *
     * @return Instance of movie favorite data access object.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideMovieFavoriteDao(movieDatabase: MovieDatabase) =
        movieDatabase.movieFavoriteDao()

    /**
     * Create a provider method binding for [MovieFavoriteRepository].
     *
     * @return Instance of movie favorite repository.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideMovieFavoriteRepository(
        movieFavoriteDao: MovieFavoriteDao
    ) = MovieFavoriteRepository(movieFavoriteDao)
}
