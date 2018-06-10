package co.androidbaseappkotlinmvvm.favorites

import android.arch.lifecycle.MutableLiveData
import co.androidbaseappkotlinmvvm.base.BaseViewModel
import co.androidbaseappkotlinmvvm.common.SingleLiveEvent
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.usecases.GetFavoriteMovies
import co.androidbaseappkotlinmvvm.entities.Movie

class FavoriteMoviesViewModel(private val getFavoriteMovies: GetFavoriteMovies,
                              private val movieEntityMovieMapper: Mapper<MovieEntity, Movie>) : BaseViewModel() {

    var viewState: MutableLiveData<FavoritesMoviesViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = FavoritesMoviesViewState()
        this.viewState.value = viewState
    }

    fun getFavorites() {
        getFavoriteMovies.observable()
                .flatMap { movieEntityMovieMapper.observable(it) }
                .subscribe({ movies ->
                    val newViewState = viewState.value?.copy(
                            isEmpty = movies.isEmpty(),
                            isLoading = false,
                            movies = movies)
                    viewState.value = newViewState
                    errorState.value = null

                }, {
                    viewState.value = viewState.value?.copy(isLoading = false, isEmpty = false)
                    errorState.value = it

                })
    }

}