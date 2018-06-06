package co.androidbaseappkotlinmvvm.di.favorites

import co.androidbaseappkotlinmvvm.MovieEntityMovieMapper
import co.androidbaseappkotlinmvvm.common.ASyncTransformer
import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.usecases.GetFavoriteMovies
import co.androidbaseappkotlinmvvm.di.DI
import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FavoriteModule {

    @Provides
    fun provideGetFavoriteMovies(@Named(DI.favoritesCache) moviesCache: MoviesCache): GetFavoriteMovies {
        return GetFavoriteMovies(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideFavoriteMoviesVMFactory(getFavoriteMovies: GetFavoriteMovies,
                                       movieEntityMoveMapper: MovieEntityMovieMapper): FavoriteMoviesVMFactory {
        return FavoriteMoviesVMFactory(getFavoriteMovies, movieEntityMoveMapper)
    }
}