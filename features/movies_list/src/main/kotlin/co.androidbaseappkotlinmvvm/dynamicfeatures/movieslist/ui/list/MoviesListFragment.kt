/*
 * Copyright 2020 tecruz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import co.androidbaseappkotlinmvvm.MovieApp.Companion.coreComponent
import co.androidbaseappkotlinmvvm.commons.ui.base.BaseFragment
import co.androidbaseappkotlinmvvm.commons.ui.extensions.gridLayoutManager
import co.androidbaseappkotlinmvvm.commons.ui.extensions.observe
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.R
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.databinding.FragmentMoviesListBinding
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.adapter.MoviesListAdapter
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.adapter.MoviesListAdapterState
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.di.MoviesListModule
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.model.MovieItem
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.di.DaggerMoviesListComponent
import javax.inject.Inject

/**
 * View listing the all movies with option to display the detail view.
 *
 * @see BaseFragment
 */
class MoviesListFragment :
    BaseFragment<FragmentMoviesListBinding, MoviesListViewModel>(
        layoutId = R.layout.fragment_movies_list
    ) {

    @Inject
    lateinit var viewAdapter: MoviesListAdapter

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerMoviesListComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .moviesListModule(MoviesListModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.moviesList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    // ============================================================================================
    //  Private observers methods
    // ============================================================================================

    /**
     * Observer view data change on [MoviesListViewModel].
     *
     * @param viewData Paged list of movies.
     */
    private fun onViewDataChange(viewData: PagedList<MovieItem>) {
        viewAdapter.submitList(viewData)
    }

    /**
     * Observer view state change on [MoviesListViewModel].
     *
     * @param viewState State of movies list.
     */
    private fun onViewStateChange(viewState: MoviesListViewState) {
        when (viewState) {
            is MoviesListViewState.Loaded ->
                viewAdapter.submitState(MoviesListAdapterState.Added)
            is MoviesListViewState.AddLoading ->
                viewAdapter.submitState(MoviesListAdapterState.AddLoading)
            is MoviesListViewState.AddError ->
                viewAdapter.submitState(MoviesListAdapterState.AddError)
            is MoviesListViewState.NoMoreElements ->
                viewAdapter.submitState(MoviesListAdapterState.NoMore)
        }
    }

    /**
     * Observer view event change on [MoviesListViewModel].
     *
     * @param viewEvent Event on movies list.
     */
    private fun onViewEvent(viewEvent: MoviesListViewEvent) {
        when (viewEvent) {
            is MoviesListViewEvent.OpenMovieDetail ->
                findNavController().navigate(
                    MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(
                        viewEvent.id
                    )
                )
        }
    }
}
