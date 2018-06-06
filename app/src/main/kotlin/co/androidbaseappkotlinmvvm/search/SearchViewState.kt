package co.androidbaseappkotlinmvvm.search

import co.androidbaseappkotlinmvvm.entities.Movie

data class SearchViewState(
        val isLoading: Boolean = false,
        val movies: List<Movie>? = null,
        val lastSearchedQuery: String? = null,
        val showNoResultsMessage: Boolean = false
)