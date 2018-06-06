package co.androidbaseappkotlinmvvm.di.popular

import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesFragment
import dagger.Subcomponent

@Subcomponent(modules = [PopularMoviesModule::class])
interface PopularSubComponent {
    fun inject(popularMoviesFragment: PopularMoviesFragment)
}