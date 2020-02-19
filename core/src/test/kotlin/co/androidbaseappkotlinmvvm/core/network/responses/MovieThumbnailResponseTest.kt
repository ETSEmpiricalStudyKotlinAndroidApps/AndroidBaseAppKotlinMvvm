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

import org.junit.Assert
import org.junit.Test

class MovieThumbnailResponseTest {

    @Test
    fun createMovieThumbnailResponse_ShouldAddCorrectAttributes() {
        val path = "https://image.tmdb.org/t/p/w342/vj4IhmH4HCMZYYjTMiYBybTWR5o.jpg"

        val movieThumbnailResponse = MovieThumbnailResponse(
            path = path
        )

        Assert.assertEquals(path, movieThumbnailResponse.path)
    }
}
