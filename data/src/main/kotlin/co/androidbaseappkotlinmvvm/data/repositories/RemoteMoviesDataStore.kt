package co.androidbaseappkotlinmvvm.data.repositories

import co.androidbaseappkotlinmvvm.data.api.Api
import co.androidbaseappkotlinmvvm.data.entities.DetailsData
import co.androidbaseappkotlinmvvm.data.entities.MovieData
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.MoviesDataStore
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class RemoteMoviesDataStore(private val api: Api,
                            private val movieDataMapper: Mapper<MovieData, MovieEntity>,
                            private val detailedDataMapper: Mapper<DetailsData, MovieEntity>) : MoviesDataStore {

    override fun search(query: String): Observable<List<MovieEntity>> {
        return api.searchMovies(query).map { results ->
            results.movies.map { movieDataMapper.mapFrom(it) }
        }
    }

    override fun getMovies(): Observable<List<MovieEntity>> {
        return api.getPopularMovies().map { results ->
            results.movies.map { movieDataMapper.mapFrom(it) }
        }
    }

    override fun getMovieById(movieId: Int): Observable<Optional<MovieEntity>> {
        return api.getMovieDetails(movieId).flatMap { detailedData ->
            Observable.just(Optional.of(detailedDataMapper.mapFrom(detailedData)))
        }
    }
}