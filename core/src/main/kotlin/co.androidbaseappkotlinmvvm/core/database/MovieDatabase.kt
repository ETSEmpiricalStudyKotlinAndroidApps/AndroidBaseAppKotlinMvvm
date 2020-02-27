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

package co.androidbaseappkotlinmvvm.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.androidbaseappkotlinmvvm.core.BuildConfig
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao

/**
 * Movie room database storing the different requested information from movies
 *
 * @see Database
 */
@Database(
    entities = [MovieFavorite::class],
    exportSchema = BuildConfig.MOVIE_DATABASE_EXPORT_SCHEMA,
    version = BuildConfig.MOVIE_DATABASE_VERSION
)
abstract class MovieDatabase : RoomDatabase() {

    /**
     * Get movie favorite data access object.
     *
     * @return Movie favorite dao.
     */
    abstract fun movieFavoriteDao(): MovieFavoriteDao
}
