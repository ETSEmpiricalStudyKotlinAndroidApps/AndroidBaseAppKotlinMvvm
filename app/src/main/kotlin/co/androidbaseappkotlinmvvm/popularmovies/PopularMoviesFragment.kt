package co.androidbaseappkotlinmvvm.popularmovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.base.BaseFragment
import co.androidbaseappkotlinmvvm.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import javax.inject.Inject

class PopularMoviesFragment : BaseFragment<PopularMoviesViewModel>() {

    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    private var popularMoviesFragmentInteractionListener: PopularMoviesFragmentInteractionListener? = null

    override fun getViewModelClass(): Class<PopularMoviesViewModel> {
        return PopularMoviesViewModel::class.java
    }

    override fun layoutId(): Int {
        return R.layout.fragment_popular_movies
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getPopularMovies()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                popularMoviesFragmentInteractionListener?.showErrorMessage(throwable)
            }
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PopularMoviesFragmentInteractionListener) {
            popularMoviesFragmentInteractionListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }



    override fun onDetach() {
        super.onDetach()
        popularMoviesFragmentInteractionListener = null
    }

    private fun handleViewState(state: PopularMoviesViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.movies?.let { popularMoviesAdapter.addMovies(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = popular_movies_progress
        popularMoviesAdapter = PopularMoviesAdapter(imageLoader, { movie, view ->
            navigateToMovieDetailsScreen(movie, view)
        })
        recyclerView = popular_movies_recyclerview
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = popularMoviesAdapter
    }

    interface PopularMoviesFragmentInteractionListener {
        fun showErrorMessage(exception: Throwable)
    }
}