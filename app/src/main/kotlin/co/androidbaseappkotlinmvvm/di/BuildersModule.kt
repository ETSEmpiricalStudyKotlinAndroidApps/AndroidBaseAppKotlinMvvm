package co.androidbaseappkotlinmvvm.di

import co.androidbaseappkotlinmvvm.MainActivity
import co.androidbaseappkotlinmvvm.details.MovieDetailsActivity
import co.androidbaseappkotlinmvvm.di.modules.MovieDetailsModule
import co.androidbaseappkotlinmvvm.di.modules.FavoriteModule
import co.androidbaseappkotlinmvvm.di.modules.MainModule
import co.androidbaseappkotlinmvvm.di.modules.PopularMoviesModule
import co.androidbaseappkotlinmvvm.di.modules.SearchMoviesModule
import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesFragment
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesFragment
import co.androidbaseappkotlinmvvm.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [PopularMoviesModule::class])
    abstract fun bindPopularMoviesFragment(): PopularMoviesFragment

    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    abstract fun bindFavoriteMoviesFragment(): FavoriteMoviesFragment

    @ContributesAndroidInjector(modules = [SearchMoviesModule::class])
    abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector(modules = [MovieDetailsModule::class])
    abstract fun bindMovieDetailsActivity(): MovieDetailsActivity
}