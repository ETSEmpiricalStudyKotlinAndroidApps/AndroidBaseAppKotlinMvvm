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

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import javax.inject.Inject

/**
 * Repository module for handling movie favorite data operations [MovieFavoriteDao].
 */
class MovieFavoriteRepository @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val movieFavoriteDao: MovieFavoriteDao
) {

    /**
     * Obtain all database added favorite movies ordering by name field.
     *
     * @return [LiveData] List with favorite movies.
     */
    fun getAllMoviesFavoriteLiveData(): LiveData<List<MovieFavorite>> =
        movieFavoriteDao.getAllMoviesFavoriteLiveData()

    /**
     * Obtain all database added favorite movies ordering by name field.
     *
     * @return List with favorite movies.
     */
    suspend fun getAllMoviesFavorite(): List<MovieFavorite> =
        movieFavoriteDao.getAllMoviesFavorite()

    /**
     * Obtain database favorite movie by identifier.
     *
     * @param movieId Movie identifier.
     *
     * @return Favorite movie if exist, otherwise null
     */
    suspend fun getMovieFavorite(movieFavoriteId: Long): MovieFavorite? =
        movieFavoriteDao.getMovieFavorite(movieFavoriteId)

    /**
     * Delete all database favorite movies.
     */
    suspend fun deleteAllMoviesFavorite() =
        movieFavoriteDao.deleteAllMoviesFavorite()

    /**
     * Delete database favorite movie by identifier.
     *
     * @param movieFavoriteId Movie identifier.
     */
    suspend fun deleteMovieFavoriteById(movieFavoriteId: Long) =
        movieFavoriteDao.deleteMovieFavoriteById(movieFavoriteId)

    /**
     * Delete database favorite movie.
     *
     * @param movie Movie favorite.
     */
    suspend fun deleteMovieFavorite(movie: MovieFavorite) =
        movieFavoriteDao.deleteMovieFavorite(movie)

    /**
     * Add to database a list of favorite movies.
     *
     * @param movies List of favorite movies.
     */
    suspend fun insertMoviesFavorites(movies: List<MovieFavorite>) =
        movieFavoriteDao.insertMoviesFavorites(movies)

    /**
     * Add to database a favorite movie.
     *
     * @param id Movie identifier.
     * @param name Movie name.
     * @param imageUrl Movie thumbnail url.
     */
    suspend fun insertMovieFavorite(id: Long, name: String, imageUrl: String) {
        val movieFavorite = MovieFavorite(
            id = id,
            name = name,
            imageUrl = imageUrl
        )
        movieFavoriteDao.insertMovieFavorite(movieFavorite)
    }
}
