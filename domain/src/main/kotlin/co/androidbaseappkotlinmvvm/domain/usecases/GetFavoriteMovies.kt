package co.androidbaseappkotlinmvvm.domain.usecases

import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.common.Transformer
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import io.reactivex.Observable

class GetFavoriteMovies(transformer: Transformer<List<MovieEntity>>,
                        private val moviesCache: MoviesCache): UseCase<List<MovieEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<MovieEntity>> {
        return moviesCache.getAll()
    }

}