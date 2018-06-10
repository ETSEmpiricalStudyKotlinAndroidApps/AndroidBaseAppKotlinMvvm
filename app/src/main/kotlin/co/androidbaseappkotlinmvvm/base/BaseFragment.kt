package co.androidbaseappkotlinmvvm.base

import android.app.ActivityOptions
import android.content.Context
import android.support.v4.app.Fragment
import android.util.Pair
import android.view.View
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.details.MovieDetailsActivity
import co.androidbaseappkotlinmvvm.entities.Movie
import dagger.android.support.AndroidSupportInjection

open class BaseFragment: Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    protected fun navigateToMovieDetailsScreen(movie: Movie, view: View) {
        var activityOptions: ActivityOptions? = null

        val imageForTransition: View? = view.findViewById(R.id.image)
        imageForTransition?.let {
            val posterSharedElement: Pair<View, String> = Pair.create(it, getString(R.string.transition_poster))
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, posterSharedElement)
        }
        startActivity(MovieDetailsActivity.newIntent(
                context!!,
                movie.id,
                movie.posterPath), activityOptions?.toBundle())

        activity?.overridePendingTransition(0, 0)
    }
}