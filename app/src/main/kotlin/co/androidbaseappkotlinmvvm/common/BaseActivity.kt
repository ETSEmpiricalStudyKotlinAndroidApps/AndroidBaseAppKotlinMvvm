package co.androidbaseappkotlinmvvm.common

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.data.api.error.RetrofitException
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setSupportActionBar(toolbar)
        toolbar?.title = getString(R.string.app_name)
    }

    protected fun addFragment(fragment: BaseFragment, title: String){
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

                RetrofitException.Kind.UNEXPECTED->
                    Toast.makeText(this, R.string.default_unexpected_error_message, Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, R.string.default_error_message, Toast.LENGTH_LONG).show()
        }
    }
}