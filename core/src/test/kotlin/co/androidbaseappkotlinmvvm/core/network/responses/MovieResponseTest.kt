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

class MovieResponseTest {

    @Test
    fun createMovieResponse_ShouldAddCorrectAttributes() {
        val id = 1L
        val name = "A.I.M"
        val description = "AIM is a terrorist organization bent on destroying the world."
        val path = "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg"
        val detailPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg"

        val movieResponse = MovieResponse(
            id = id,
            name = name,
            description = description,
            image = path,
            detailImage = detailPath
        )

        Assert.assertEquals(id, movieResponse.id)
        Assert.assertEquals(name, movieResponse.name)
        Assert.assertEquals(description, movieResponse.description)
        Assert.assertEquals(path, movieResponse.image)
    }
}
