package co.androidbaseappkotlinmvvm.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.data.api.error.RetrofitException
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    inline fun <reified T : ViewModel> FragmentActivity.viewModel(
            factory: ViewModelProvider.Factory,
            body: T.() -> Unit
    ): T {
        val vm = ViewModelProviders.of(this, factory)[T::class.java]
        vm.body()
        return vm
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setSupportActionBar(toolbar)
        toolbar?.title = getString(R.string.app_name)
    }

    protected fun addFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, title)
                .commit()
    }

    protected fun showErrorToast(exception: Throwable) {
        if (exception is RetrofitException) {
            when (exception.getKind()) {

                RetrofitException.Kind.HTTP ->
                    Toast.makeText(this, R.string.default_http_error_message, Toast.LENGTH_LONG).show()

                RetrofitException.Kind.NETWORK ->
                    Toast.makeText(this, R.string.default_network_error_message, Toast.LENGTH_LONG).show()

                RetrofitException.Kind.UNEXPECTED ->
                    Toast.makeText(this, R.string.default_unexpected_error_message, Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, R.string.default_error_message, Toast.LENGTH_LONG).show()
        }
    }
}