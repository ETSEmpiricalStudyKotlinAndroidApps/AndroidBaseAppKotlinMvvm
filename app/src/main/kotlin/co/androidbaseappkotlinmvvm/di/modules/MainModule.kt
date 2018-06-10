package co.androidbaseappkotlinmvvm.di.modules

import android.app.Application
import android.content.Context
import co.androidbaseappkotlinmvvm.common.ImageLoader
import co.androidbaseappkotlinmvvm.common.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule {
    //empty because the activity don't need dependencies for now
}