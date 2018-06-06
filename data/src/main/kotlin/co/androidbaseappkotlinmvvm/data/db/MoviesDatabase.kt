package co.androidbaseappkotlinmvvm.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import co.androidbaseappkotlinmvvm.data.entities.MovieData

/**
 * Created by Yossi Segev on 20/01/2018.
 */
@Database(entities = arrayOf(MovieData::class), version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}