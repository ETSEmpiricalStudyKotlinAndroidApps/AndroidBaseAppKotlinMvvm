package co.androidbaseappkotlinmvvm.di.details

import co.androidbaseappkotlinmvvm.details.MovieDetailsActivity
import dagger.Subcomponent

@DetailsScope
@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsSubComponent {
    fun inject(movieDetailsActivity: MovieDetailsActivity)
}