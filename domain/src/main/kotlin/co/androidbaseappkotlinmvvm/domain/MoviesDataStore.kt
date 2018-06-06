package co.androidbaseappkotlinmvvm.domain

import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

interface MoviesDataStore {

    fun getMovieById(movieId: Int): Observable<Optional<MovieEntity>>
    fun getMovies(): Observable<List<MovieEntity>>
    fun search(query: String): Observable<List<MovieEntity>>
}