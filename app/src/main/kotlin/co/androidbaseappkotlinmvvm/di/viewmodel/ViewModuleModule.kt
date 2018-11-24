package co.androidbaseappkotlinmvvm.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.androidbaseappkotlinmvvm.details.MovieDetailsViewModel
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.entities.Movie
import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesViewModel
import co.androidbaseappkotlinmvvm.mapper.MovieEntityMovieMapper
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesViewModel
import co.androidbaseappkotlinmvvm.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMoviesViewModel::class)
    abstract fun bindsFavoriteMoviesViewModel(favoriteMoviesViewModel: FavoriteMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PopularMoviesViewModel::class)
    internal abstract fun bindsPopularMoviesViewModel(popularMoviesViewModel: PopularMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindsSearchViewModel(searchViewViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    internal abstract fun bindsMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel
}