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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list

import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.MoviesListViewEvent
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesListViewEventTest {

    lateinit var event: MoviesListViewEvent

    @Test
    fun setEventOpenCharacterDetail_ShouldSettledCorrectly() {
        val characterId = 1L
        event = MoviesListViewEvent.OpenMovieDetail(characterId)

        val eventOpenDetail = event as MoviesListViewEvent.OpenMovieDetail
        assertEquals(characterId, eventOpenDetail.id)
    }
}
