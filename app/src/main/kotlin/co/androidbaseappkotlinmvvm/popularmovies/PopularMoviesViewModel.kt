package co.androidbaseappkotlinmvvm.popularmovies

import android.arch.lifecycle.MutableLiveData
import co.androidbaseappkotlinmvvm.base.BaseViewModel
import co.androidbaseappkotlinmvvm.common.SingleLiveEvent
import co.androidbaseappkotlinmvvm.domain.usecases.GetPopularMovies
import javax.inject.Inject

class PopularMoviesViewModel
@Inject constructor(private val getPopularMovies: GetPopularMovies) : BaseViewModel() {

    var viewState: MutableLiveData<PopularMoviesViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = PopularMoviesViewState()
    }

    fun getPopularMovies() {
        addDisposable(getPopularMovies.observable()
                .flatMap { movieEntityMovieMapper.observable(it) }
                .subscribe({ movies ->
                    viewState.value?.let {
                        val newState = this.viewState.value?.copy(showLoading = false, movies = movies)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }

                }, {
                    viewState.value = viewState.value?.copy(showLoading = false)
                    errorState.value = it
                }))
    }
}