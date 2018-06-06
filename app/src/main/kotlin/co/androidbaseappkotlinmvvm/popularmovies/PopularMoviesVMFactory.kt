package co.androidbaseappkotlinmvvm.popularmovies

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.usecases.GetPopularMovies
import co.androidbaseappkotlinmvvm.entities.Movie

/**
 * Created by Yossi Segev on 01/01/2018.
 */
class PopularMoviesVMFactory(private val useCase: GetPopularMovies,
                             private val mapper: Mapper<MovieEntity, Movie>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularMoviesViewModel(useCase, mapper) as T
    }

}