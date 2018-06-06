package co.androidbaseappkotlinmvvm.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.usecases.CheckFavoriteStatus
import co.androidbaseappkotlinmvvm.domain.usecases.GetMovieDetails
import co.androidbaseappkotlinmvvm.domain.usecases.RemoveFavoriteMovie
import co.androidbaseappkotlinmvvm.domain.usecases.SaveFavoriteMovie
import co.androidbaseappkotlinmvvm.entities.Movie

class MovieDetailsVMFactory(
        private val getMovieDetails: GetMovieDetails,
        private val saveFavoriteMovie: SaveFavoriteMovie,
        private val removeFavoriteMovie: RemoveFavoriteMovie,
        private val checkFavoriteStatus: CheckFavoriteStatus,
        private val mapper: Mapper<MovieEntity, Movie>) : ViewModelProvider.Factory {

    var movieId: Int = -1

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(
                getMovieDetails,
                saveFavoriteMovie,
                removeFavoriteMovie,
                checkFavoriteStatus,
                mapper,
                movieId) as T //TODO: solve casting issue
    }

}