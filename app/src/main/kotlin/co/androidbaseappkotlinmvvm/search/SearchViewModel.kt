package co.androidbaseappkotlinmvvm.search

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import co.androidbaseappkotlinmvvm.base.BaseViewModel
import co.androidbaseappkotlinmvvm.common.SingleLiveEvent
import co.androidbaseappkotlinmvvm.domain.usecases.SearchMovie
import javax.inject.Inject

class SearchViewModel
@Inject constructor(private val searchMovie: SearchMovie) : BaseViewModel() {

    var viewState: MutableLiveData<SearchViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = SearchViewState()
    }

    fun search(query: String) {

        errorState.value = null

        if (query.isEmpty()) {
            viewState.value = viewState.value?.copy(
                    isLoading = false,
                    showNoResultsMessage = false,
                    lastSearchedQuery = query,
                    movies = null)
        } else {
            viewState.value = viewState.value?.copy(
                    isLoading = true,
                    showNoResultsMessage = false)

            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        Log.d(javaClass.simpleName, "Searching $query")

        addDisposable(searchMovie.search(query)
                .flatMap { movieEntityMovieMapper.observable(it) }
                .subscribe({ movies ->
                    viewState.value = viewState.value?.copy(
                            isLoading = false,
                            movies = movies,
                            lastSearchedQuery = query,
                            showNoResultsMessage = movies.isEmpty())

                }, {
                    viewState.value = viewState.value?.copy(
                            isLoading = false,
                            movies = null,
                            lastSearchedQuery = query
                    )
                    errorState.value = it
                }))
    }


}