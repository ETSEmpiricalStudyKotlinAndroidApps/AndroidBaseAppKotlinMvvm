package co.androidbaseappkotlinmvvm.data.api.error

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response

class NetInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        val originalResponse = chain!!.proceed(chain.request())

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (networkInfo?.isConnected!!) {
            throw NetworkException("There is no Internet connection")
        }
        return originalResponse
    }
}