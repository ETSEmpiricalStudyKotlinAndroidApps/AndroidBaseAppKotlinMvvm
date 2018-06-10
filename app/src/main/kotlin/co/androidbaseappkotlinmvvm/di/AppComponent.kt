package co.androidbaseappkotlinmvvm.di

import android.app.Application
import co.androidbaseappkotlinmvvm.di.modules.AppModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import co.androidbaseappkotlinmvvm.App
import co.androidbaseappkotlinmvvm.di.modules.DataModule
import co.androidbaseappkotlinmvvm.di.modules.NetworkModule
import dagger.BindsInstance

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AppModule::class),
    (BuildersModule::class),
    (NetworkModule::class),
    (DataModule::class)
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun networkModule(networkModule: NetworkModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}