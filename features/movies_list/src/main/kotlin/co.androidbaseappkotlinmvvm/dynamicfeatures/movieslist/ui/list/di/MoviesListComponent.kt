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

import co.androidbaseappkotlinmvvm.core.di.CoreComponent
import co.androidbaseappkotlinmvvm.core.di.scopes.FeatureScope
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.MoviesListFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [MoviesListModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [MoviesListModule::class],
    dependencies = [CoreComponent::class])
interface MoviesListComponent {

    /**
     * Inject dependencies on component.
     *
     * @param listFragment Movies list component.
     */
    fun inject(listFragment: MoviesListFragment)
}
