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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.adapter

import co.androidbaseappkotlinmvvm.commons.ui.base.BaseViewState

/**
 * Different states for [MoviesListAdapter].
 *
 * @see BaseViewState
 */
sealed class MoviesListAdapterState(
    val hasExtraRow: Boolean
) {

    /**
     * Listed the added movies into list.
     */
    object Added : MoviesListAdapterState(hasExtraRow = true)

    /**
     * Loading for new movies to add into list.
     */
    object AddLoading : MoviesListAdapterState(hasExtraRow = true)

    /**
     * Error on add new movies into list.
     */
    object AddError : MoviesListAdapterState(hasExtraRow = true)

    /**
     * No more movies to add into list.
     */
    object NoMore : MoviesListAdapterState(hasExtraRow = false)

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Added].
     *
     * @return True if is added state, otherwise false.
     */
    fun isAdded() = this is Added

    /**
     * Check if current view state is [AddLoading].
     *
     * @return True if is add loading state, otherwise false.
     */
    fun isAddLoading() = this is AddLoading

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError

    /**
     * Check if current view state is [NoMore].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMore() = this is NoMore
}
