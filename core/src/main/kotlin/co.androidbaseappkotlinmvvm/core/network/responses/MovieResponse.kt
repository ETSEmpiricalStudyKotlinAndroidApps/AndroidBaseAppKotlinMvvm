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

package co.androidbaseappkotlinmvvm.core.network.responses

import co.androidbaseappkotlinmvvm.core.annotations.OpenForTesting
import com.google.gson.annotations.SerializedName

/**
 * Movie API network response item.
 *
 * @param id The unique ID of the movies resource.
 * @param name The name of the movie.
 * @param description A short bio or description of the movie.
 * @param image The representative image for this movie.
 * @param detailImage The representative detail image for this movie.
 */
@OpenForTesting
data class MovieResponse(
    val id: Long,
    @SerializedName("original_title")
    val name: String,
    @SerializedName("overview")
    val description: String,
    @SerializedName("poster_path")
    val image: String,
    @SerializedName("backdrop_path")
    val detailImage: String
) : BaseResponse(null, null)
