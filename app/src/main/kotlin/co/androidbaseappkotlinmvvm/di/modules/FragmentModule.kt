package co.androidbaseappkotlinmvvm.di.modules

import co.androidbaseappkotlinmvvm.di.scopes.favorites.FavoritesScope
import co.androidbaseappkotlinmvvm.di.scopes.popularmovies.PopularMoviesScope
import co.androidbaseappkotlinmvvm.di.scopes.search.SearchScope
import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesFragment
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesFragment
import co.androidbaseappkotlinmvvm.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector()
    @FavoritesScope
    abstract fun favoriteMoviesFragment(): FavoriteMoviesFragment

    @ContributesAndroidInjector()
    @PopularMoviesScope
    abstract fun popularMoviesFragment(): PopularMoviesFragment

    @ContributesAndroidInjector()
    @SearchScope
    abstract fun searchFragment(): SearchFragment
}