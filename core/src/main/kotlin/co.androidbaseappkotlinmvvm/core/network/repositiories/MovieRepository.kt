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

package co.androidbaseappkotlinmvvm.core.network.repositiories

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import co.androidbaseappkotlinmvvm.core.BuildConfig
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.ResultsResponse
import co.androidbaseappkotlinmvvm.core.network.services.MovieService

private const val API_KEY = BuildConfig.MOVIE_API_KEY

/**
 * Repository module for handling Movie API network operations [MovieService].
 */
class MovieRepository(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val service: MovieService
) {

    /**
     * Get all info of movie.
     *
     * @param id A single movie id.
     * @return Response for single movie resource.
     */
    suspend fun getMovie(id: Long): MovieResponse {
        return service.getMovie(
            id = id,
            apiKey = API_KEY)
    }

    /**
     * Get all movie characters paged.
     *
     * @param page Limit the result set to the specified number of resources.
     * @return Response for comic characters resource.
     */
    suspend fun getMovies(page: Int): ResultsResponse<MovieResponse> {
        return service.getMovies(
            apiKey = API_KEY,
            page = page)
    }
}
