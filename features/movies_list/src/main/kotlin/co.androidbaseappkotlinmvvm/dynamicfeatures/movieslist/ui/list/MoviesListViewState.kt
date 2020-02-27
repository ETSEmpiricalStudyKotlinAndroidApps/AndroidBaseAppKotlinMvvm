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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list

import co.androidbaseappkotlinmvvm.commons.ui.base.BaseViewState

/**
 * Different states for [MoviesListFragment].
 *
 * @see BaseViewState
 */
sealed class MoviesListViewState : BaseViewState {

    /**
     * Refreshing movies list.
     */
    object Refreshing : MoviesListViewState()

    /**
     * Loaded movies list.
     */
    object Loaded : MoviesListViewState()

    /**
     * Loading movies list.
     */
    object Loading : MoviesListViewState()

    /**
     * Loading on add more elements into movies list.
     */
    object AddLoading : MoviesListViewState()

    /**
     * Empty movies list.
     */
    object Empty : MoviesListViewState()

    /**
     * Error on loading movies list.
     */
    object Error : MoviesListViewState()

    /**
     * Error on add more elements into movies list.
     */
    object AddError : MoviesListViewState()

    /**
     * No more elements for adding into movies list.
     */
    object NoMoreElements : MoviesListViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Refreshing].
     *
     * @return True if is refreshing state, otherwise false.
     */
    fun isRefreshing() = this is Refreshing

    /**
     * Check if current view state is [Loaded].
     *
     * @return True if is loaded state, otherwise false.
     */
    fun isLoaded() = this is Loaded

    /**
     * Check if current view state is [Loading].
     *
     * @return True if is loading state, otherwise false.
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [AddLoading].
     *
     * @return True if is add loading state, otherwise false.
     */
    fun isAddLoading() = this is AddLoading

    /**
     * Check if current view state is [Empty].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isEmpty() = this is Empty

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError

    /**
     * Check if current view state is [NoMoreElements].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMoreElements() = this is NoMoreElements
}
