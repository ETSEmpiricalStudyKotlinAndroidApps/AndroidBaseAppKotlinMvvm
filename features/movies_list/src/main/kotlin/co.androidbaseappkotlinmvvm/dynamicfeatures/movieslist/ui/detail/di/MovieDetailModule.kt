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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import co.androidbaseappkotlinmvvm.commons.ui.extensions.viewModel
import co.androidbaseappkotlinmvvm.commons.views.ProgressBarDialog
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteRepository
import co.androidbaseappkotlinmvvm.core.di.scopes.FeatureScope
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.MovieDetailFragment
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.MovieDetailViewModel
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.detail.model.MovieDetailMapper
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [MovieDetailComponent].
 *
 * @see Module
 */
@Module
class MovieDetailModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: MovieDetailFragment
) {

    /**
     * Create a provider method binding for [MovieDetailViewModel].
     *
     * @param movieRepository
     * @param movieFavoriteRepository handler movie favorite repository
     * @param movieDetailMapper mapper to parse view model
     *
     * @return instance of view model.
     */
    @FeatureScope
    @Provides
    fun providesMovieDetailViewModel(
        movieRepository: MovieRepository,
        movieFavoriteRepository: MovieFavoriteRepository,
        movieDetailMapper: MovieDetailMapper
    ) = fragment.viewModel {
        MovieDetailViewModel(
            movieRepository = movieRepository,
            movieFavoriteRepository = movieFavoriteRepository,
            movieDetailMapper = movieDetailMapper
        )
    }

    /**
     * Create a provider method binding for [MovieDetailMapper].
     *
     * @return instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesMovieDetailMapper() = MovieDetailMapper()

    /**
     * Create a provider method binding for [ProgressBarDialog].
     *
     * @return instance of dialog.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesProgressBarDialog() = ProgressBarDialog(fragment.requireContext())
}
