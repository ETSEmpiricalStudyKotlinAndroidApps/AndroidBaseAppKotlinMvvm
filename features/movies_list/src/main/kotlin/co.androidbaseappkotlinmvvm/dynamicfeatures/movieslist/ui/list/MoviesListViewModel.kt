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

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import co.androidbaseappkotlinmvvm.commons.ui.livedata.SingleLiveData
import co.androidbaseappkotlinmvvm.core.network.NetworkState
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.paging.MoviesPageDataSourceFactory
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.paging.PAGE_INIT_ELEMENTS
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [MoviesListFragment].
 *
 * @see ViewModel
 */
class MoviesListViewModel
@Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val dataSourceFactory: MoviesPageDataSourceFactory
) : ViewModel() {

    @VisibleForTesting(otherwise = PRIVATE)
    val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<MoviesListViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_INIT_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    MoviesListViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    MoviesListViewState.Empty
                } else {
                    MoviesListViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    MoviesListViewState.AddLoading
                } else {
                    MoviesListViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    MoviesListViewState.AddError
                } else {
                    MoviesListViewState.Error
                }
            else -> MoviesListViewState.Error
        }
    }

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Refresh movies fetch them again and update the list.
     */
    fun refreshLoadedMoviesList() {
        dataSourceFactory.refresh()
    }

    /**
     * Retry last fetch operation to add movies into list.
     */
    fun retryAddMoviesList() {
        dataSourceFactory.retry()
    }

    /**
     * Send interaction event for open movie detail view from selected movie.
     *
     * @param movieId Movie identifier.
     */
    fun openMovieDetail(movieId: Long) {
        event.postValue(MoviesListViewEvent.OpenMovieDetail(movieId))
    }
}
