package co.androidbaseappkotlinmvvm.di.modules

import android.content.Context
import co.androidbaseappkotlinmvvm.common.ImageLoader
import co.androidbaseappkotlinmvvm.common.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(context: Context){

    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideImageLoader(context: Context) : ImageLoader {
        return PicassoImageLoader(Picasso.with(context))
    }
}