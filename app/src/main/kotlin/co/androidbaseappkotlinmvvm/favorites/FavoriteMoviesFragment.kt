package co.androidbaseappkotlinmvvm.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.base.BaseFragment
import co.androidbaseappkotlinmvvm.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_favorite_movies.*
import javax.inject.Inject

class FavoriteMoviesFragment : BaseFragment() {

    @Inject
    lateinit var factory: FavoriteMoviesVMFactory
    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var viewModel: FavoriteMoviesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(FavoriteMoviesViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: FavoritesMoviesViewState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (!state.isLoading && state.isEmpty) View.VISIBLE else View.GONE
        state.movies?.let { favoriteMoviesAdapter.setMovies(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = favorite_movies_progress
        favoriteMoviesAdapter = FavoriteMoviesAdapter(imageLoader, { movie, view ->
            navigateToMovieDetailsScreen(movie, view)
        })
        recyclerView = favorite_movies_recyclerview
        emptyMessage = favorite_movies_empty_message
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = favoriteMoviesAdapter

    }
}