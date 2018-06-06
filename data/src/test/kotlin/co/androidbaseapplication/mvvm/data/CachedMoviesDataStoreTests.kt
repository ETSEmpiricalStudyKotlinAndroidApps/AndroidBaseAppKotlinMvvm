package co.androidbaseapplication.mvvm.data

import co.androidbaseappkotlinmvvm.data.repositories.CachedMoviesDataStore
import co.androidbaseappkotlinmvvm.data.repositories.MemoryMoviesCache
import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.common.DomainTestUtils.Companion.generateMovieEntityList
import co.androidbaseappkotlinmvvm.domain.common.DomainTestUtils.Companion.getTestMovieEntity
import org.junit.After
import org.junit.Before
import org.junit.Test

class CachedMoviesDataStoreTests {

    private lateinit var moviesCache: MoviesCache
    private lateinit var cachedMoviesDataStore: CachedMoviesDataStore

    @Before
    fun before() {
        moviesCache = MemoryMoviesCache()
        cachedMoviesDataStore = CachedMoviesDataStore(moviesCache)
    }

    @After
    fun after() {
        moviesCache.clear()
    }

    @Test
    fun testWhenSavingMoviesInCacheTheyCanBeRetrieved() {
        moviesCache.saveAll(generateMovieEntityList())
        cachedMoviesDataStore.getMovies().test()
                .assertValue { list -> list.size == 5 }
                .assertComplete()
    }

    @Test
    fun testSavedMovieCanBeRetrievedUsingId() {
        moviesCache.save(getTestMovieEntity(1))
        cachedMoviesDataStore.getMovieById(1).test()
                .assertValue { optional -> optional.hasValue() && optional.value?.id == 1 }
                .assertComplete()
    }

    @Test
    fun testWhenRetrievingIdThatDoesNotExistsReturnEmptyOptional() {
        moviesCache.saveAll(generateMovieEntityList())
        cachedMoviesDataStore.getMovieById(18877).test()
                .assertValue { optional -> !optional.hasValue() }
                .assertComplete()
    }
}