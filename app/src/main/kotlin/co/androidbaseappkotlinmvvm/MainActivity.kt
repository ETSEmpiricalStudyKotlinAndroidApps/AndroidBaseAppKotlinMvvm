package co.androidbaseappkotlinmvvm

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import co.androidbaseappkotlinmvvm.base.BaseActivity
import co.androidbaseappkotlinmvvm.favorites.FavoriteMoviesFragment
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesFragment
import co.androidbaseappkotlinmvvm.popularmovies.PopularMoviesViewModel
import co.androidbaseappkotlinmvvm.search.SearchFragment
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, PopularMoviesFragment.PopularMoviesFragmentInteractionListener, SearchFragment.SearchFragmentInteractionListener, HasSupportFragmentInjector {

    private lateinit var navigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(PopularMoviesFragment(), getString(R.string.popular))

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {

            R.id.action_popular -> {
                addFragment(PopularMoviesFragment(), getString(R.string.popular))
            }

            R.id.action_favorites -> {
                addFragment(FavoriteMoviesFragment(), getString(R.string.my_favorites))
            }

            R.id.action_search -> {
                addFragment(SearchFragment(), getString(R.string.search))
            }
        }

        return true
    }

    override fun showErrorMessage(exception: Throwable) {
        showErrorToast(exception)
    }
}
