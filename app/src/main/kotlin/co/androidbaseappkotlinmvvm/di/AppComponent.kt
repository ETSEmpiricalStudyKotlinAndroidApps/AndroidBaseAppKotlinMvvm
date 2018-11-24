package co.androidbaseappkotlinmvvm.di

import android.app.Application
import co.androidbaseappkotlinmvvm.di.modules.ActivityModule
import co.androidbaseappkotlinmvvm.di.modules.AppModule
import co.androidbaseappkotlinmvvm.di.modules.DataModule
import co.androidbaseappkotlinmvvm.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AppModule::class),
    (ActivityModule::class)
])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}