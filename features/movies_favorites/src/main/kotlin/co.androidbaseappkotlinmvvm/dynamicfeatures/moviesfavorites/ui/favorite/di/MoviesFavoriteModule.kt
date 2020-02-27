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

package co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import co.androidbaseappkotlinmvvm.commons.ui.extensions.viewModel
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.core.di.scopes.FeatureScope
import co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite.MoviesFavoriteFragment
import co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite.MoviesFavoriteViewModel
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.adapter.MoviesFavoriteAdapter
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [MoviesFavoriteComponent].
 *
 * @see Module
 */
@Module
class MoviesFavoriteModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: MoviesFavoriteFragment
) {

    /**
     * Create a provider method binding for [MoviesFavoriteViewModel].
     *
     * @param movieFavoriteRepository Handler character favorite repository.
     * @return Instance of view model.
     * @see Provides
     */
    @Provides
    @FeatureScope
    fun providesCharactersFavoriteViewModel(
        movieFavoriteRepository: MovieFavoriteRepository
    ) = fragment.viewModel {
        MoviesFavoriteViewModel(
            movieFavoriteRepository = movieFavoriteRepository
        )
    }

    /**
     * Create a provider method binding for [MoviesFavoriteAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @Provides
    @FeatureScope
    fun providesCharactersFavoriteAdapter() = MoviesFavoriteAdapter()
}
