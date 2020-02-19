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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.viewModelScope
import co.androidbaseappkotlinmvvm.commons.ui.extensions.viewModel
import co.androidbaseappkotlinmvvm.core.di.scopes.FeatureScope
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.MoviesListFragment
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.MoviesListViewModel
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.adapter.CharactersListAdapter
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.model.CharacterItemMapper
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.paging.CharactersPageDataSource
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.paging.MoviesPageDataSourceFactory
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [MoviesListComponent].
 *
 * @see Module
 */
@Module
class MoviesListModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: MoviesListFragment
) {

    /**
     * Create a provider method binding for [MoviesListViewModel].
     *
     * @param dataFactory Data source factory for movies.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesMoviesListViewModel(
        dataFactory: MoviesPageDataSourceFactory
    ) = fragment.viewModel {
        MoviesListViewModel(dataFactory)
    }

    /**
     * Create a provider method binding for [CharactersPageDataSource].
     *
     * @return Instance of data source.
     * @see Provides
     */
    @Provides
    fun providesCharactersPageDataSource(
        viewModel: MoviesListViewModel,
        repository: MovieRepository,
        mapper: CharacterItemMapper
    ) = CharactersPageDataSource(
            repository = repository,
            scope = viewModel.viewModelScope,
            mapper = mapper
        )

    /**
     * Create a provider method binding for [CharacterItemMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesCharacterItemMapper() = CharacterItemMapper()

    /**
     * Create a provider method binding for [CharactersListAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesCharactersListAdapter(
        viewModel: MoviesListViewModel
    ) = CharactersListAdapter(viewModel)
}
