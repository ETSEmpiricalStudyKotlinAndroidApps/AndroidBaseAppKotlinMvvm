package co.androidbaseappkotlinmvvm

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import android.app.Activity
import android.util.Base64
import co.androidbaseappkotlinmvvm.di.DaggerAppComponent
import co.androidbaseappkotlinmvvm.di.modules.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }

        DaggerAppComponent
                .builder()
                .application(this)
                .networkModule(NetworkModule(getString(R.string.base_url), String(Base64.decode(getApiKey(), Base64.DEFAULT))))
                .build()
                .inject(this);
    }

    external fun getApiKey() : String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}