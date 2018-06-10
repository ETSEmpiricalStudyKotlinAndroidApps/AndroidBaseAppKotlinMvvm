package co.androidbaseappkotlinmvvm.data.repositories

import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.MoviesDataStore
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

class CachedMoviesDataStore(private val moviesCache: MoviesCache): MoviesDataStore {

    override fun search(query: String): Observable<List<MovieEntity>> {
        return moviesCache.search(query)
    }

    override fun getMovieById(movieId: Int): Observable<Optional<MovieEntity>> {
        return moviesCache.get(movieId)
    }

    override fun getMovies(): Observable<List<MovieEntity>> {
        return moviesCache.getAll()
    }

}