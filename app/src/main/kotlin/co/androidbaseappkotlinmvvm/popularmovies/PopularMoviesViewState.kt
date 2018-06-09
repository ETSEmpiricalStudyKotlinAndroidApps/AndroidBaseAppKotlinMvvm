package co.androidbaseappkotlinmvvm.popularmovies

import co.androidbaseappkotlinmvvm.entities.Movie

data class PopularMoviesViewState(
        var showLoading: Boolean = true,
        var movies: List<Movie>? = null
)
