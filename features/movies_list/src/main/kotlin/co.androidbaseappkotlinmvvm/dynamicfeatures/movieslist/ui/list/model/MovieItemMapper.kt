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
import co.androidbaseappkotlinmvvm.core.network.responses.BaseResponse
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse

private const val IMAGE_URL_FORMAT = "%s.%s"

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
open class MovieItemMapper : Mapper<BaseResponse<MovieResponse>, List<MovieItem>> {

    /**
     * Transform network response to [MovieItem].
     *
     * @param from Network characters response.
     * @return List of parsed characters items.
     */
    override suspend fun map(from: BaseResponse<MovieResponse>) =
        from.data.results.map {
            MovieItem(
                id = it.id,
                name = it.name,
                description = it.description,
                imageUrl = IMAGE_URL_FORMAT.format(
                    it.thumbnail.path.replace("http", "https"),
                    it.thumbnail.extension
                )
            )
        }
}
