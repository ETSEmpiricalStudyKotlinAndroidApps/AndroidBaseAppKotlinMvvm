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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.model.MovieDetail
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.model.MovieDetailMapper
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * View model responsible for preparing and managing the data for [MovieDetailFragment].
 *
 * @see ViewModel
 */
class MovieDetailViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val movieRepository: MovieRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val movieFavoriteRepository: MovieFavoriteRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val movieDetailMapper: MovieDetailMapper
) : ViewModel() {

    private val _data = MutableLiveData<MovieDetail>()
    val data: LiveData<MovieDetail>
        get() = _data

    private val _state = MutableLiveData<MovieDetailViewState>()
    val state: LiveData<MovieDetailViewState>
        get() = _state

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Fetch selected movie detail info.
     *
     * @param movieId Movie identifier.
     */
    fun loadMovieDetail(movieId: Long) {
        _state.postValue(MovieDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val result = movieRepository.getMovie(movieId)
                _data.postValue(movieDetailMapper.map(result))

                movieFavoriteRepository.getMovieFavorite(movieId)?.let {
                    _state.postValue(MovieDetailViewState.AlreadyAddedToFavorite)
                } ?: run {
                    _state.postValue(MovieDetailViewState.AddToFavorite)
                }
            } catch (e: Exception) {
                _state.postValue(MovieDetailViewState.Error)
            }
        }
    }

    /**
     * Store selected movie to database favorite list.
     */
    fun addMovieToFavorite() {
        _data.value?.let {
            viewModelScope.launch {
                movieFavoriteRepository.insertMovieFavorite(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl
                )
                _state.postValue(MovieDetailViewState.AddedToFavorite)
            }
        }
    }

    /**
     * Send interaction event for dismiss movie detail view.
     */
    fun dismissMovieDetail() {
        _state.postValue(MovieDetailViewState.Dismiss)
    }
}
