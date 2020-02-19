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

package co.androidbaseappkotlinmvvm.core.di

import android.content.Context
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavoriteDao
import co.androidbaseappkotlinmvvm.core.di.modules.ContextModule
import co.androidbaseappkotlinmvvm.core.di.modules.DatabaseModule
import co.androidbaseappkotlinmvvm.core.di.modules.NetworkModule
import co.androidbaseappkotlinmvvm.core.di.modules.UtilsModule
import co.androidbaseappkotlinmvvm.core.network.repositiories.MovieRepository
import co.androidbaseappkotlinmvvm.core.network.services.MovieService
import co.androidbaseappkotlinmvvm.core.utils.ThemeUtils
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(modules = [
    ContextModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    UtilsModule::class
])
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph MovieService
     *
     * @return MovieService
     */
    fun movieService(): MovieService

    /**
     * Provide dependency graph MovieRepository
     *
     * @return MovieRepository
     */
    fun movieRepository(): MovieRepository

    /**
     * Provide dependency graph MovieFavoriteDao
     *
     * @return MovieFavoriteDao
     */
    fun movieFavoriteDao(): MovieFavoriteDao

    /**
     * Provide dependency graph ThemeUtils
     *
     * @return ThemeUtils
     */
    fun themeUtils(): ThemeUtils
}
