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

package co.androidbaseappkotlinmvvm.core.database.charactersfavorite

import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieFavoriteTest {

    @Test
    fun createCharacterFavorite_ShouldAddCorrectAttributes() {
        val characterId = 0L
        val characterName = "A.I.M"
        val characterImageUrl = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg"

        val characterFavorite = MovieFavorite(
            id = characterId,
            name = characterName,
            imageUrl = characterImageUrl
        )

        assertEquals(characterId, characterFavorite.id)
        assertEquals(characterName, characterFavorite.name)
        assertEquals(characterImageUrl, characterFavorite.imageUrl)
    }
}
