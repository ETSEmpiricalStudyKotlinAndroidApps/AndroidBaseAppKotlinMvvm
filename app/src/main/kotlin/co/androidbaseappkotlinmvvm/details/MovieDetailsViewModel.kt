package co.androidbaseappkotlinmvvm.details

import android.arch.lifecycle.MutableLiveData
import co.androidbaseappkotlinmvvm.base.BaseViewModel
import co.androidbaseappkotlinmvvm.common.SingleLiveEvent
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.domain.usecases.CheckFavoriteStatusUsecase
import co.androidbaseappkotlinmvvm.domain.usecases.GetMovieDetails
import co.androidbaseappkotlinmvvm.domain.usecases.RemoveFavoriteMovieUsecase
import co.androidbaseappkotlinmvvm.domain.usecases.SaveFavoriteMovie
import co.androidbaseappkotlinmvvm.entities.Movie
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(private val getMovieDetails: GetMovieDetails,
                    private val saveFavoriteMovie: SaveFavoriteMovie,
                    private val removeFavoriteMovieUsecase: RemoveFavoriteMovieUsecase,
                    private val checkFavoriteStatusUsecase: CheckFavoriteStatusUsecase) : BaseViewModel() {

    lateinit var movieEntity: MovieEntity
    var viewState: MutableLiveData<MovieDetailsViewState> = MutableLiveData()
    var favoriteState: MutableLiveData<Boolean> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var movieId = -1

    init {
        viewState.value = MovieDetailsViewState(isLoading = true)
    }

    fun getMovieDetails() {
        addDisposable(
                getMovieDetails.getById(movieId)
                        .map {
                            it.value?.let {
                                movieEntity = it
                                movieEntityMovieMapper.mapFrom(movieEntity)
                            } ?: run {
                                throw Throwable("Something went wrong :(")
                            }
                        }
                        .zipWith(checkFavoriteStatusUsecase.check(movieId), BiFunction<Movie, Boolean, Movie> { movie, isFavorite ->
                            movie.isFavorite = isFavorite
                            return@BiFunction movie
                        })
                        .subscribe(
                                { onMovieDetailsReceived(it) },
                                { errorState.value = it }
                        )
        )
    }

    fun favoriteButtonClicked() {
        addDisposable(checkFavoriteStatusUsecase.check(movieId).flatMap {
            when (it) {
                true -> {
                    removeFavorite(movieEntity)
                }
                false -> {
                    saveFavorite(movieEntity)
                }
            }
        }.subscribe({ isFavorite ->
            favoriteState.value = isFavorite
        }, {
            errorState.value = it
        }))
    }

    private fun onMovieDetailsReceived(movie: Movie) {

        val newViewState = viewState.value?.copy(
                isLoading = false,
                title = movie.originalTitle,
                videos = movie.details?.videos,
                homepage = movie.details?.homepage,
                overview = movie.details?.overview,
                releaseDate = movie.releaseDate,
                votesAverage = movie.voteAverage,
                backdropUrl = movie.backdropPath,
                genres = movie.details?.genres)

        viewState.value = newViewState
        favoriteState.value = movie.isFavorite
    }

    private fun saveFavorite(movieEntity: MovieEntity): Observable<Boolean> {
        return saveFavoriteMovie.save(movieEntity)
    }

    private fun removeFavorite(movieEntity: MovieEntity): Observable<Boolean> {
        return removeFavoriteMovieUsecase.remove(movieEntity)
    }
}