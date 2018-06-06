package co.androidbaseappkotlinmvvm.di.favorites

import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesFragment
import dagger.Subcomponent

@FavoritesScope
@Subcomponent(modules = [FavoriteModule::class])
interface FavoritesSubComponent {
    fun inject(favoriteMoviesFragment: FavoriteMoviesFragment)
}