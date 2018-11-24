package co.androidbaseappkotlinmvvm.di.modules

import co.androidbaseappkotlinmvvm.common.ASyncTransformer
import co.androidbaseappkotlinmvvm.di.DI
import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.MoviesRepository
import co.androidbaseappkotlinmvvm.domain.usecases.*
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UseCaseModule {

    @Provides
    fun provideGetFavoriteMovies(@Named(DI.favoritesCache) moviesCache: MoviesCache): GetFavoriteMovies {
        return GetFavoriteMovies(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideSearchMovieUseCase(moviesRepository: MoviesRepository): SearchMovie {
        return SearchMovie(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository): GetPopularMovies {
        return GetPopularMovies(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(moviesRepository: MoviesRepository): GetMovieDetails {
        return GetMovieDetails(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideSaveFavoriteMovieUseCase(@Named(DI.favoritesCache) moviesCache: MoviesCache): SaveFavoriteMovie {
        return SaveFavoriteMovie(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideRemoveFavoriteMovieUseCase(@Named(DI.favoritesCache) moviesCache: MoviesCache): RemoveFavoriteMovieUsecase {
        return RemoveFavoriteMovieUsecase(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideCheckFavoriteStatusUseCase(@Named(DI.favoritesCache) moviesCache: MoviesCache): CheckFavoriteStatusUsecase {
        return CheckFavoriteStatusUsecase(ASyncTransformer(), moviesCache)
    }
}