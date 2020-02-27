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

import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.ResultsResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieItemMapperTest {

    private val mapper = MovieItemMapper()

    @Test
    fun movieMapper_WithEmptyResults_ShouldReturnEmptyList() = runBlocking {
        val response = ResultsResponse<MovieResponse>(
            total = 0,
            results = emptyList()
        )

        assertTrue(mapper.map(response).isNullOrEmpty())
    }

    @Test
    fun movieMapper_WithResults_ShouldReturnParsedList() = runBlocking {
        val response = ResultsResponse<MovieResponse>(
            total = 0,
            results = listOf(
                MovieResponse(
                    id = 1011334,
                    name = "3-D Man",
                    description = "",
                    image = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                    detailImage = ""
                )
            )
        )

        mapper.map(response).first().run {
            assertEquals(1011334, this.id)
            assertEquals("3-D Man", this.name)
            assertEquals("", this.description)
            assertEquals("https://image.tmdb.org/t/p/w500/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                this.imageUrl)
        }
    }
}
