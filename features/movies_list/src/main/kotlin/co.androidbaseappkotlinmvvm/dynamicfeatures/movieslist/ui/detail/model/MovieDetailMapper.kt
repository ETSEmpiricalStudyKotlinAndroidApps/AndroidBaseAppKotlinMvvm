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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.model

import co.androidbaseappkotlinmvvm.core.mapper.Mapper
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342%s"

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class MovieDetailMapper : Mapper<MovieResponse, MovieDetail> {

    /**
     * Transform network response to [MovieDetail].
     *
     * @param from Network movies response.
     * @return List of parsed movies items.
     * @throws NoSuchElementException If the response results are empty.
     */
    @Throws(NoSuchElementException::class)
    override suspend fun map(from: MovieResponse): MovieDetail {
        return MovieDetail(
            id = from.id,
            name = from.name,
            description = from.description,
            imageUrl = IMAGE_BASE_URL.format(from.detailImage)
        )
    }
}
