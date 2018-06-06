package co.androidbaseappkotlinmvvm.di.search

import co.androidbaseappkotlinmvvm.search.SearchFragment
import dagger.Subcomponent

@SearchScope
@Subcomponent(modules = [SearchMoviesModule::class])
interface SearchSubComponent {
    fun inject(searchFragment: SearchFragment)
}