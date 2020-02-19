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

package co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import co.androidbaseappkotlinmvvm.MovieApp
import co.androidbaseappkotlinmvvm.commons.ui.base.BaseFragment
import co.androidbaseappkotlinmvvm.commons.ui.extensions.observe
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.R
import co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.databinding.FragmentMoviesFavoriteListBinding
import co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite.di.DaggerMoviesFavoriteComponent
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.adapter.MoviesFavoriteAdapter
import co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.adapter.MoviesFavoriteTouchHelper
import co.androidbaseappkotlinmvvm.dynamicfeatures.moviesfavorites.ui.favorite.di.MoviesFavoriteModule
import javax.inject.Inject

/**
 * View listing the added favorite movies with option to remove element by swiping.
 *
 * @see BaseFragment
 */
class MoviesFavoriteFragment :
    BaseFragment<FragmentMoviesFavoriteListBinding, MoviesFavoriteViewModel>(
        layoutId = R.layout.fragment_movies_favorite_list
    ) {

    @Inject
    lateinit var viewAdapter: MoviesFavoriteAdapter

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
        observe(viewModel.data, ::onViewDataChange)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerMoviesFavoriteComponent
            .builder()
            .coreComponent(MovieApp.coreComponent(requireContext()))
            .moviesFavoriteModule(MoviesFavoriteModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.moviesFavoriteList.apply {
            adapter = viewAdapter

            ItemTouchHelper(MoviesFavoriteTouchHelper {
                viewModel.removeFavoriteMovie(viewAdapter.currentList[it])
            }).attachToRecyclerView(this)
        }
    }

    // ============================================================================================
    //  Private observers methods
    // ============================================================================================

    /**
     * Observer view data change on [MoviesFavoriteViewModel].
     *
     * @param viewData List of favorite characters.
     */
    private fun onViewDataChange(viewData: List<MovieFavorite>) {
        viewAdapter.submitList(viewData)
    }
}
