package co.androidbaseappkotlinmvvm.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.usecases.SearchMovie
import co.androidbaseappkotlinmvvm.entities.Movie

class SearchVMFactory(val searchMovie: SearchMovie,
                      val mapper: Mapper<MovieEntity, Movie>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchMovie, mapper) as T
    }

}