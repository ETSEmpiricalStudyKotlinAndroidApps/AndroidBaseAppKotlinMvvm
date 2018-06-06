package co.androidbaseappkotlinmvvm.domain

import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

interface MoviesCache {

    fun clear()
    fun save(movieEntity: MovieEntity)
    fun remove(movieEntity: MovieEntity)
    fun saveAll(movieEntities: List<MovieEntity>)
    fun getAll(): Observable<List<MovieEntity>>
    fun get(movieId: Int): Observable<Optional<MovieEntity>>
    fun search(query: String): Observable<List<MovieEntity>>
    fun isEmpty(): Observable<Boolean>
}