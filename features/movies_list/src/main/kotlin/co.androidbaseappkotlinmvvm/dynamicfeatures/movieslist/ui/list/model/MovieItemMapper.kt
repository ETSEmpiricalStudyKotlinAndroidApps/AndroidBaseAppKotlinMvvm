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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.model

import co.androidbaseappkotlinmvvm.core.mapper.Mapper
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.ResultsResponse

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342%s"

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
open class MovieItemMapper : Mapper<ResultsResponse<MovieResponse>, List<MovieItem>> {

    /**
     * Transform network response to [MovieItem].
     *
     * @param from Network movies response.
     * @return List of parsed movies items.
     */
    override suspend fun map(from: ResultsResponse<MovieResponse>) =
        from.results.map {
            MovieItem(
                id = it.id,
                name = it.name,
                description = it.description,
                imageUrl = IMAGE_BASE_URL.format(it.image)
            )
        }
}
