package co.androidbaseappkotlinmvvm.data.mappers

import co.androidbaseappkotlinmvvm.data.entities.MovieData
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 22/01/2018.
 */
@Singleton
class MovieEntityDataMapper @Inject constructor() : Mapper<MovieEntity, MovieData>() {

    override fun mapFrom(from: MovieEntity): MovieData {
        return MovieData(
                id = from.id,
                voteCount = from.voteCount,
                voteAverage = from.voteAverage,
                popularity = from.popularity,
                adult = from.adult,
                title = from.title,
                posterPath = from.posterPath,
                originalLanguage = from.originalLanguage,
                backdropPath = from.backdropPath,
                originalTitle = from.originalTitle,
                releaseDate = from.releaseDate,
                overview = from.overview
        )
    }
}