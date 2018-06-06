package co.androidbaseappkotlinmvvm.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import co.androidbaseappkotlinmvvm.data.api.Api
import co.androidbaseappkotlinmvvm.data.db.MoviesDatabase
import co.androidbaseappkotlinmvvm.data.db.RoomFavoritesMovieCache
import co.androidbaseappkotlinmvvm.data.mappers.DetailsDataMovieEntityMapper
import co.androidbaseappkotlinmvvm.data.mappers.MovieDataEntityMapper
import co.androidbaseappkotlinmvvm.data.mappers.MovieEntityDataMapper
import co.androidbaseappkotlinmvvm.data.repositories.MemoryMoviesCache
import co.androidbaseappkotlinmvvm.data.repositories.CachedMoviesDataStore
import co.androidbaseappkotlinmvvm.data.repositories.MoviesRepositoryImpl
import co.androidbaseappkotlinmvvm.data.repositories.RemoteMoviesDataStore
import co.androidbaseappkotlinmvvm.domain.MoviesCache
import co.androidbaseappkotlinmvvm.domain.MoviesDataStore
import co.androidbaseappkotlinmvvm.domain.MoviesRepository
import co.androidbaseappkotlinmvvm.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
@Singleton
class DataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
                context,
                MoviesDatabase::class.java,
                "movies_db").build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: Api,
                               @Named(DI.inMemoryCache) cache: MoviesCache,
                               movieDataMapper: MovieDataEntityMapper,
                               detailedDataMapper: DetailsDataMovieEntityMapper): MoviesRepository {

        return MoviesRepositoryImpl(api, cache, movieDataMapper, detailedDataMapper)
    }

    @Singleton
    @Provides
    @Named(DI.inMemoryCache)
    fun provideInMemoryMoviesCache(): MoviesCache {
        return MemoryMoviesCache()
    }

    @Singleton
    @Provides
    @Named(DI.favoritesCache)
    fun provideFavoriteMoviesCache(moviesDatabase: MoviesDatabase,
                                   entityDataMapper: MovieEntityDataMapper,
                                   dataEntityMapper: MovieDataEntityMapper): MoviesCache {
        return RoomFavoritesMovieCache(moviesDatabase, entityDataMapper, dataEntityMapper)
    }

    @Singleton
    @Provides
    @Named(DI.remoteDataStore)
    fun provideRemoteMovieDataStore(api: Api, movieDataMapper: MovieDataEntityMapper, detailedDataMapper: DetailsDataMovieEntityMapper): MoviesDataStore {
        return RemoteMoviesDataStore(api, movieDataMapper, detailedDataMapper)
    }

    @Singleton
    @Provides
    @Named(DI.cachedDataStore)
    fun provideCachedMoviesDataStore(moviesCache: MoviesCache): MoviesDataStore {
        return CachedMoviesDataStore(moviesCache)
    }
}