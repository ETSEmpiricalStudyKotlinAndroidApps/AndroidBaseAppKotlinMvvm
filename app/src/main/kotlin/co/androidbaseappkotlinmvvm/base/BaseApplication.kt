package co.androidbaseappkotlinmvvm.base

import co.androidbaseappkotlinmvvm.di.AppComponent
import dagger.android.support.DaggerApplication

abstract class BaseApplication : DaggerApplication() {

    abstract val appComponent: AppComponent

    override fun applicationInjector() = appComponent
}