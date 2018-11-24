package co.androidbaseappkotlinmvvm.di.modules

import co.androidbaseappkotlinmvvm.MainActivity
import co.androidbaseappkotlinmvvm.details.MovieDetailsActivity
import co.androidbaseappkotlinmvvm.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    @ActivityScope
    abstract fun mainActivity(): MainActivity

    @ActivityScope
    abstract fun movieDetailsActivity(): MovieDetailsActivity
}