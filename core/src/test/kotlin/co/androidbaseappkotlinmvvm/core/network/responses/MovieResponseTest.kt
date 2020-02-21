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
    fun createCharacterResponse_ShouldAddCorrectAttributes() {
        val id = 1L
        val name = "A.I.M"
        val description = "AIM is a terrorist organization bent on destroying the world."
        val path = "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg"


        val characterResponse = MovieResponse(
            id = id,
            name = name,
            description = description,
            image = path
        )

        Assert.assertEquals(id, characterResponse.id)
        Assert.assertEquals(name, characterResponse.name)
        Assert.assertEquals(description, characterResponse.description)
        Assert.assertEquals(path, characterResponse.image)
    }
}
