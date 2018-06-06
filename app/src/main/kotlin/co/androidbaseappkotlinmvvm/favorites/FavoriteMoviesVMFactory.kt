package co.androidbaseappkotlinmvvm.favorites

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.usecases.GetFavoriteMovies
import co.androidbaseappkotlinmvvm.entities.Movie

class FavoriteMoviesVMFactory(private val useCase: GetFavoriteMovies,
                              private val mapper: Mapper<MovieEntity, Movie>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteMoviesViewModel(useCase, mapper) as T
    }

}