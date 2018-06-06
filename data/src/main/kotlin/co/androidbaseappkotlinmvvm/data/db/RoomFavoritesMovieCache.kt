package co.androidbaseappkotlinmvvm.data.db

import co.androidbaseappkotlinmvvm.data.entities.MovieData
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 22/01/2018.
 */
class RoomFavoritesMovieCache(database: MoviesDatabase,
                              private val entityToDataMapper: Mapper<MovieEntity, MovieData>,
                              private val dataToEntityMapper: Mapper<MovieData, MovieEntity>) : MoviesCache {
    private val dao: MoviesDao = database.getMoviesDao()

    override fun clear() {
        dao.clear()
    }

    override fun save(movieEntity: MovieEntity) {
        dao.saveMovie(entityToDataMapper.mapFrom(movieEntity))
    }

    override fun remove(movieEntity: MovieEntity) {
        dao.removeMovie(entityToDataMapper.mapFrom(movieEntity))
    }

    override fun saveAll(movieEntities: List<MovieEntity>) {
        dao.saveAllMovies(movieEntities.map { entityToDataMapper.mapFrom(it) })
    }

    override fun getAll(): Observable<List<MovieEntity>> {
        return Observable.fromCallable { dao.getFavorites().map { dataToEntityMapper.mapFrom(it) } }
    }

    override fun get(movieId: Int): Observable<Optional<MovieEntity>> {

        return Observable.fromCallable {
            val movieData = dao.get(movieId)
            movieData?.let {
                Optional.of(dataToEntityMapper.mapFrom(it))
            } ?: Optional.empty()
        }
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.fromCallable { dao.getFavorites().isEmpty() }
    }

    override fun search(query: String): Observable<List<MovieEntity>> {
        val searchQuery = "%$query%"
        return Observable.fromCallable {
            dao.search(searchQuery).map { dataToEntityMapper.mapFrom(it) }
        }
    }
}