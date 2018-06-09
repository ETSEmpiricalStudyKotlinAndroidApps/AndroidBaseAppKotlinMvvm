package co.androidbaseappkotlinmvvm

import android.os.Bundle
import android.support.design.widget.BottomNavigationView

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import co.androidbaseappkotlinmvvm.R.id.bottomNavigationView
import co.androidbaseappkotlinmvvm.common.BaseActivity
import co.androidbaseappkotlinmvvm.common.BaseFragment
import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesFragment
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesFragment
import co.androidbaseappkotlinmvvm.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, PopularMoviesFragment.OnFragmentInteractionListener {

    private lateinit var navigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(PopularMoviesFragment(), "popular")

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {

            R.id.action_popular -> {
                addFragment(PopularMoviesFragment(), "popular")
            }

            R.id.action_favorites -> {
                addFragment(FavoriteMoviesFragment(), "favorites")
            }

            R.id.action_search -> {
                addFragment(SearchFragment(), "search")
            }
        }

        return true
    }

    override fun showErrorMessage(exception: Throwable) {
        showErrorToast(exception)
    }


}
