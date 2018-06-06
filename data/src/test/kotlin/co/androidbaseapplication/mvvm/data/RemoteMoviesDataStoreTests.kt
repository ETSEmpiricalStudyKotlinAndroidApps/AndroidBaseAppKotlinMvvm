package co.androidbaseapplication.mvvm.data

import co.androidbaseappkotlinmvvm.data.api.Api
import co.androidbaseappkotlinmvvm.data.api.MovieListResult
import co.androidbaseappkotlinmvvm.data.entities.DetailsData
import co.androidbaseappkotlinmvvm.data.entities.MovieData
import co.androidbaseappkotlinmvvm.data.mappers.DetailsDataMovieEntityMapper
import co.androidbaseappkotlinmvvm.data.mappers.MovieDataEntityMapper
import co.androidbaseappkotlinmvvm.data.repositories.RemoteMoviesDataStore
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("IllegalIdentifier")
class RemoteMoviesDataStoreTests {

    private val movieDataMovieEntityMapper = MovieDataEntityMapper()
    private val detailsDataMovieEntityMapper = DetailsDataMovieEntityMapper()
    private lateinit var api: Api
    private lateinit var remoteMoviesDataStore: RemoteMoviesDataStore

    @Before
    fun before() {
        api = mock(Api::class.java)
        remoteMoviesDataStore = RemoteMoviesDataStore(
                api,
                movieDataMovieEntityMapper,
                detailsDataMovieEntityMapper)
    }

    @Test
    fun testWhenRequestingPopularMoviesFromRemoteReturnExpectedResult() {

        val popularMovies = (0..4).map {
            MovieData(
                    id = it,
                    title = "Movie$it",
                    backdropPath = "",
                    originalLanguage = "",
                    posterPath = "",
                    originalTitle = "",
                    releaseDate = ""
            )
        }

        val movieListResult = MovieListResult()
        movieListResult.movies = popularMovies
        movieListResult.page = 1
        `when`(api.getPopularMovies()).thenReturn(Observable.just(movieListResult))

        remoteMoviesDataStore.getMovies().test()
                .assertValue { list -> list.size == 5 && list[0].title == "Movie0" }
                .assertComplete()
    }

    @Test
    fun testWhenRequestingMovieFromRemoteReturnExpectedValue() {

        val movieDetailedData = DetailsData(
                id = 1,
                title = "Movie1",
                backdropPath = "",
                originalLanguage = "",
                overview = "",
                posterPath = "",
                originalTitle = "",
                releaseDate = ""
        )

        `when`(api.getMovieDetails(1)).thenReturn(Observable.just(movieDetailedData))
        remoteMoviesDataStore.getMovieById(1).test()
                .assertValue { optional ->
                    optional.hasValue() &&
                            optional.value?.title == "Movie1" && optional.value?.id == 1
                }
                .assertComplete()
    }

    @Test
    fun testSearchingMovieReturnsExpectedResults() {
        val searchResults = (0..2).map {
            MovieData(
                    id = it,
                    title = "Movie$it",
                    backdropPath = "",
                    originalLanguage = "",
                    posterPath = "",
                    originalTitle = "",
                    releaseDate = ""
            )
        }

        val movieListResult = MovieListResult()
        movieListResult.movies = searchResults

        `when`(api.searchMovies("test query")).thenReturn(Observable.just(movieListResult))
        remoteMoviesDataStore.search("test query").test()
                .assertValue { results -> results.size == 3 }
                .assertComplete()
    }
}