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

package co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.MoviesFavoriteViewState
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * View model responsible for preparing and managing the data for [MoviesFavoriteFragment].
 *
 * @see ViewModel
 */
class MoviesFavoriteViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val movieFavoriteRepository: MovieFavoriteRepository
) : ViewModel() {

    val data = movieFavoriteRepository.getAllMoviesFavoriteLiveData()
    val state = Transformations.map(data) {
        if (it.isEmpty()) {
            MoviesFavoriteViewState.Empty
        } else {
            MoviesFavoriteViewState.Listed
        }
    }

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Remove the selected favorite character from database in case if exist.
     *
     * @param movie Favorite character.
     */
    fun removeFavoriteCharacter(movie: MovieFavorite) {
        viewModelScope.launch {
            movieFavoriteRepository.deleteCharacterFavorite(movie)
        }
    }
}
