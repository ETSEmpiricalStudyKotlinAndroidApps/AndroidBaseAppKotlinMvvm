package co.androidbaseappkotlinmvvm.di.modules

import co.androidbaseappkotlinmvvm.mapper.MovieEntityMovieMapper
import co.androidbaseappkotlinmvvm.common.ASyncTransformer
import co.androidbaseappkotlinmvvm.domain.MoviesRepository
import co.androidbaseappkotlinmvvm.domain.usecases.SearchMovie
import co.androidbaseappkotlinmvvm.search.SearchVMFactory
import dagger.Module
import dagger.Provides

@Module
class SearchMoviesModule {

    @Provides
    fun provideSearchMovieUseCase(moviesRepository: MoviesRepository): SearchMovie {
        return SearchMovie(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideSearchVMFactory(useCase: SearchMovie, mapper: MovieEntityMovieMapper): SearchVMFactory {
        return SearchVMFactory(useCase, mapper)
    }
}