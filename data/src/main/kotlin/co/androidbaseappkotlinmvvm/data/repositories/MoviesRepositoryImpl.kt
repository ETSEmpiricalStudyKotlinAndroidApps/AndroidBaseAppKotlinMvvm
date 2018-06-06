package co.androidbaseappkotlinmvvm.data.repositories

import co.androidbaseappkotlinmvvm.data.api.Api
import co.androidbaseappkotlinmvvm.data.entities.DetailsData
import co.androidbaseappkotlinmvvm.data.entities.MovieData
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.MoviesDataStore
import co.androidbaseappkotlinmvvm.domain.MoviesRepository
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 25/01/2018.
 */

class MoviesRepositoryImpl(api: Api,
                           private val cache: MoviesCache,
                           movieDataMapper: Mapper<MovieData, MovieEntity>,
                           detailedDataMapper: Mapper<DetailsData, MovieEntity>) : MoviesRepository {

    private val memoryDataStore: MoviesDataStore
    private val remoteDataStore: MoviesDataStore

    init {
        memoryDataStore = CachedMoviesDataStore(cache)
        remoteDataStore = RemoteMoviesDataStore(api, movieDataMapper, detailedDataMapper)
    }

    override fun getMovies(): Observable<List<MovieEntity>> {

        return cache.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap memoryDataStore.getMovies()
            }
            else {
                return@flatMap remoteDataStore.getMovies().doOnNext { cache.saveAll(it) }
            }
        }
    }

    override fun search(query: String): Observable<List<MovieEntity>> {
        return remoteDataStore.search(query)
    }

    override fun getMovie(movieId: Int): Observable<Optional<MovieEntity>> {
        return remoteDataStore.getMovieById(movieId)
    }

}