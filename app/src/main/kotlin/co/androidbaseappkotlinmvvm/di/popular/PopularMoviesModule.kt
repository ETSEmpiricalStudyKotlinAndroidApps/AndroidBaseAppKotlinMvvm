package co.androidbaseappkotlinmvvm.di.popular

import co.androidbaseappkotlinmvvm.common.mapper.MovieEntityMovieMapper
import co.androidbaseappkotlinmvvm.common.ASyncTransformer
import co.androidbaseappkotlinmvvm.domain.MoviesRepository
import co.androidbaseappkotlinmvvm.domain.usecases.GetPopularMovies
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesVMFactory
import dagger.Module
import dagger.Provides

@PopularScope
@Module
class PopularMoviesModule {
    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository): GetPopularMovies {
        return GetPopularMovies(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun providePopularMoviesVMFactory(useCase: GetPopularMovies, mapper: MovieEntityMovieMapper): PopularMoviesVMFactory {
        return PopularMoviesVMFactory(useCase, mapper)
    }
}