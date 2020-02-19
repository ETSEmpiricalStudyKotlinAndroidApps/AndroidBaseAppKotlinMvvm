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

package co.androidbaseappkotlinmvvm.core.database.moviefavorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * The data access object for the [MovieFavorite] class.
 *
 * @see Dao
 */
@Dao
interface MovieFavoriteDao {

    /**
     * Obtain all database added favorite movies ordering by name field.
     *
     * @return [LiveData] List with favorite movies.
     */
    @Query("SELECT * FROM movie_favorite ORDER BY name")
    fun getAllMoviesFavoriteLiveData(): LiveData<List<MovieFavorite>>

    /**
     * Obtain all database added favorite movies ordering by name field.
     *
     * @return List with favorite movies.
     */
    @Query("SELECT * FROM movie_favorite ORDER BY name")
    suspend fun getAllMoviesFavorite(): List<MovieFavorite>

    /**
     * Obtain database favorite movie by identifier.
     *
     * @param movieFavoriteId Movie identifier.
     *
     * @return Favorite movie if exist, otherwise null.
     */
    @Query("SELECT * FROM movie_favorite WHERE id = :movieFavoriteId")
    suspend fun getMovieFavorite(movieFavoriteId: Long): MovieFavorite?

    /**
     * Delete all database favorite movies.
     */
    @Query("DELETE FROM movie_favorite")
    suspend fun deleteAllMoviesFavorite()

    /**
     * Delete database favorite movie by identifier.
     *
     * @param movieFavoriteId Movie identifier.
     */
    @Query("DELETE FROM movie_favorite WHERE id = :movieFavoriteId")
    suspend fun deleteMovieFavoriteById(movieFavoriteId: Long)

    /**
     * Delete database favorite movie.
     *
     * @param movie movie favorite.
     */
    @Delete
    suspend fun deleteMovieFavorite(movie: MovieFavorite)

    /**
     * Add to database a list of favorite movies.
     *
     * @param movies List of favorite movies.
     */
    @Insert
    suspend fun insertMoviesFavorites(movies: List<MovieFavorite>)

    /**
     * Add to database a favorite movie.
     *
     * @param movie Favorite movie.
     */
    @Insert
    suspend fun insertMovieFavorite(movie: MovieFavorite)
}
