package co.androidbaseappkotlinmvvm

import android.util.Base64
import co.androidbaseappkotlinmvvm.base.BaseApplication
import co.androidbaseappkotlinmvvm.di.AppComponent
import co.androidbaseappkotlinmvvm.di.DaggerAppComponent
import co.androidbaseappkotlinmvvm.di.modules.NetworkModule

class App : BaseApplication() {

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .application(this)
                .networkModule(NetworkModule(getString(R.string.base_url), String(Base64.decode(getApiKey(), Base64.DEFAULT))))
                .build()
    }

    external fun getApiKey(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}