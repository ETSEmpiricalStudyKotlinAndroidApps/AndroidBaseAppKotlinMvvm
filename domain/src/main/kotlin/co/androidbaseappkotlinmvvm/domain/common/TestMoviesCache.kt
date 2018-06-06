package co.androidbaseappkotlinmvvm.domain.common

import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

class TestMoviesCache : MoviesCache {

    private val movies: HashMap<Int, MovieEntity> = HashMap()

    override fun search(query: String): Observable<List<MovieEntity>> {
        return Observable.just(movies.values.toList())
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.fromCallable {  movies.isEmpty() }
    }

    override fun remove(movieEntity: MovieEntity) {
        movies.remove(movieEntity.id)
    }

    override fun clear() {
        movies.clear()
    }

    override fun save(movieEntity: MovieEntity) {
        movies[movieEntity.id] = movieEntity
    }

    override fun saveAll(movieEntities: List<MovieEntity>) {
        movieEntities.forEach { movieEntity -> this.movies[movieEntity.id] = movieEntity }
    }

    override fun getAll(): Observable<List<MovieEntity>> {
        return Observable.just(movies.values.toList())
    }

    override fun get(movieId: Int): Observable<Optional<MovieEntity>> {
        return Observable.just(Optional.of(movies[movieId]))
    }
}

