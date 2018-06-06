package co.androidbaseappkotlinmvvm.common

import android.app.Application
import co.androidbaseappkotlinmvvm.di.MainComponent
import com.squareup.leakcanary.LeakCanary
import co.androidbaseappkotlinmvvm.di.details.MovieDetailsModule
import co.androidbaseappkotlinmvvm.di.details.MovieDetailsSubComponent
import co.androidbaseappkotlinmvvm.di.favorites.FavoriteModule
import co.androidbaseappkotlinmvvm.di.favorites.FavoritesSubComponent
import co.androidbaseappkotlinmvvm.di.modules.AppModule
import co.androidbaseappkotlinmvvm.di.modules.DataModule
import co.androidbaseappkotlinmvvm.di.modules.NetworkModule
import co.androidbaseappkotlinmvvm.di.popular.PopularMoviesModule
import co.androidbaseappkotlinmvvm.di.popular.PopularSubComponent
import co.androidbaseappkotlinmvvm.di.search.SearchMoviesModule
import co.androidbaseappkotlinmvvm.di.search.SearchSubComponent
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.di.DaggerMainComponent

class App: Application() {

    lateinit var mainComponent: MainComponent
    private var popularMoviesComponent: PopularSubComponent? = null
    private var favoriteMoviesComponent: FavoritesSubComponent? = null
    private var movieDetailsComponent: MovieDetailsSubComponent? = null
    private var searchMoviesComponent: SearchSubComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(getString(R.string.api_base_url), getString(R.string.api_key)))
                .dataModule(DataModule())
                .build()

    }

    fun createPopularComponenet(): PopularSubComponent {
        popularMoviesComponent = mainComponent.plus(PopularMoviesModule())
        return popularMoviesComponent!!
    }
    fun releasePopularComponent() {
        popularMoviesComponent = null
    }

    fun createFavoritesComponent() : FavoritesSubComponent {
        favoriteMoviesComponent = mainComponent.plus(FavoriteModule())
        return favoriteMoviesComponent!!
    }
    fun releaseFavoritesComponent() {
        favoriteMoviesComponent = null
    }

    fun createDetailsComponent(): MovieDetailsSubComponent {
        movieDetailsComponent = mainComponent.plus(MovieDetailsModule())
        return movieDetailsComponent!!
    }
    fun releaseDetailsComponent() {
        movieDetailsComponent = null
    }

    fun createSearchComponent(): SearchSubComponent {
        searchMoviesComponent = mainComponent.plus(SearchMoviesModule())
        return searchMoviesComponent!!
    }
    fun releaseSearchComponent() {
        searchMoviesComponent = null
    }
}