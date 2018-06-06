package co.androidbaseappkotlinmvvm.favorites

import co.androidbaseappkotlinmvvm.entities.Movie

data class FavoritesMoviesViewState(
        val isLoading: Boolean = true,
        val isEmpty: Boolean = true,
        val movies: List<Movie>? = null
)