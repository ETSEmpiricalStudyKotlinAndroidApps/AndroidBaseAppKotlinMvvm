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

package co.androidbaseappkotlinmvvm.core.network.services

import co.androidbaseappkotlinmvvm.core.network.responses.BaseResponse
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.ResultsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Representation interface of the Movie-Database API endpoints.
 */
interface MovieService {

    /**
     * Fetches a single movie resource. It is the canonical URI for any movie resource
     * provided by the API.
     *
     * @param id A single movie id.
     * @param apiKey A unique identifier used to authenticate all calling to an API.
     */
    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): MovieResponse

    /**
     * Fetches lists of movies with optional filters.
     *
     * @param apiKey A unique identifier used to authenticate all calling to an API.
     * @param page Page the result set.
     * @return Response for movies resource.
     */
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): ResultsResponse<MovieResponse>
}
