package co.androidbaseappkotlinmvvm.di

import co.androidbaseappkotlinmvvm.di.details.MovieDetailsModule
import co.androidbaseappkotlinmvvm.di.details.MovieDetailsSubComponent
import co.androidbaseappkotlinmvvm.di.favorites.FavoriteModule
import co.androidbaseappkotlinmvvm.di.favorites.FavoritesSubComponent
import co.androidbaseappkotlinmvvm.di.modules.AppModule
import co.androidbaseappkotlinmvvm.di.modules.DataModule
import co.androidbaseappkotlinmvvm.di.modules.NetworkModule
import co.androidbaseappkotlinmvvm.di.popular.PopularMoviesModule
import co.androidbaseappkotlinmvvm.di.popular.PopularSubComponent
import co.androidbaseappkotlinmvvm.di.search.SearchMoviesModule
import co.androidbaseappkotlinmvvm.di.search.SearchSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DataModule::class)
])

interface MainComponent {
    fun plus(popularMoviesModule: PopularMoviesModule): PopularSubComponent
    fun plus(favoriteMoviesModule: FavoriteModule): FavoritesSubComponent
    fun plus(movieDetailsModule: MovieDetailsModule): MovieDetailsSubComponent
    fun plus(searchMoviesModule: SearchMoviesModule): SearchSubComponent
}