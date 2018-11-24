package co.androidbaseappkotlinmvvm.base

import android.arch.lifecycle.ViewModel
import co.androidbaseappkotlinmvvm.domain.common.Mapper
import co.androidbaseappkotlinmvvm.domain.entities.MovieEntity
import co.androidbaseappkotlinmvvm.entities.Movie
import co.androidbaseappkotlinmvvm.mapper.MovieEntityMovieMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    protected val movieEntityMovieMapper:Mapper<MovieEntity, Movie> = MovieEntityMovieMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }
}
