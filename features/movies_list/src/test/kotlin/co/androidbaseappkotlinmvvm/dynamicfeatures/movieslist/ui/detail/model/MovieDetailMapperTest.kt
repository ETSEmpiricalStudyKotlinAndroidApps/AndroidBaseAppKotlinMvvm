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

import co.androidbaseappkotlinmvvm.core.network.responses.BaseResponse
import co.androidbaseappkotlinmvvm.core.network.responses.MovieResponse
import co.androidbaseappkotlinmvvm.core.network.responses.MovieThumbnailResponse
import co.androidbaseappkotlinmvvm.core.network.responses.DataResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailMapperTest {

    private val mapper = MovieDetailMapper()

    @Test(expected = NoSuchElementException::class)
    fun movieMapper_WithEmptyResults_ShouldThrowException() {
        runBlocking {
            val response = BaseResponse(
                code = 200,
                status = "Ok",
                message = "Ok",
                data = DataResponse<MovieResponse>(
                    offset = 0,
                    limit = 0,
                    total = 0,
                    count = 0,
                    results = emptyList()
                )
            )

            mapper.map(response)
        }
    }

    @Test
    fun movieMapper_WithResults_ShouldParseModel() = runBlocking {
        val response = BaseResponse(
            code = 200,
            status = "Ok",
            message = "Ok",
            data = DataResponse(
                offset = 0,
                limit = 0,
                total = 1,
                count = 1,
                results = listOf(
                    MovieResponse(
                        id = 1011334,
                        name = "3-D Man",
                        description = "",
                        thumbnail = MovieThumbnailResponse(
                        path = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg"
                        )
                    )
                )
            )
        )

        mapper.map(response).run {
            assertEquals(1011334, this.id)
            assertEquals("3-D Man", this.name)
            assertEquals("", this.description)
            assertEquals(
                "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                this.imageUrl
            )
        }
    }
}
