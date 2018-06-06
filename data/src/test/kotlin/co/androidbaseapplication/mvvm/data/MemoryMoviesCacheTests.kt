package co.androidbaseapplication.mvvm.data

import co.androidbaseappkotlinmvvm.data.repositories.MemoryMoviesCache
import co.androidbaseappkotlinmvvm.domain.common.DomainTestUtils.Companion.generateMovieEntityList
import co.androidbaseappkotlinmvvm.domain.common.DomainTestUtils.Companion.getTestMovieEntity
import org.junit.Before
import org.junit.Test

class MemoryMoviesCacheTests {

    private lateinit var memoryMoviesCache: MemoryMoviesCache

    @Before
    fun before() {
        memoryMoviesCache = MemoryMoviesCache()
    }

    @Test
    fun testWhenSavingPopularMoviesTheyCanAllBeRetrieved() {
        memoryMoviesCache.saveAll(generateMovieEntityList())
        memoryMoviesCache.getAll().test()
                .assertValue { list -> list.size == 5 }
                .assertComplete()
    }

    @Test
    fun testSavedMovieCanBeRetrievedUsingId() {
        memoryMoviesCache.save(getTestMovieEntity(3))
        memoryMoviesCache.get(3).test()
                .assertValue { optional ->
                    optional.hasValue() &&
                            optional.value?.title == "Movie3" && optional.value?.id == 3
                }
                .assertComplete()
    }

    @Test
    fun testAfterClearingTheRepositoryThereAreNoMovies() {
        memoryMoviesCache.saveAll(generateMovieEntityList())
        memoryMoviesCache.getAll().test()
                .assertValue { list -> list.size == 5 }

        memoryMoviesCache.clear()
        memoryMoviesCache.getAll().test()
                .assertValue { list -> list.isEmpty() }
                .assertComplete()
    }

    @Test
    fun testRemovingMovieFromCache() {
        val movieEntity = getTestMovieEntity(3)
        memoryMoviesCache.save(movieEntity)
        memoryMoviesCache.get(3).test()
                .assertValue { optional -> optional.hasValue() }

        memoryMoviesCache.remove(movieEntity)
        memoryMoviesCache.get(3).test()
                .assertValue { optional -> !optional.hasValue() }
    }

    @Test
    fun testIsEmptyReturnsExpectedResult() {
        memoryMoviesCache.isEmpty().test().assertValue{ value -> value }
        memoryMoviesCache.saveAll(generateMovieEntityList())
        memoryMoviesCache.isEmpty().test().assertValue{ value -> !value }
    }
}