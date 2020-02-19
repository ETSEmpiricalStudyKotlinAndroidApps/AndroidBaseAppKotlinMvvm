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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.androidbaseappkotlinmvvm.MovieApp.Companion.coreComponent
import com.google.android.material.snackbar.Snackbar
import co.androidbaseappkotlinmvvm.commons.ui.base.BaseFragment
import co.androidbaseappkotlinmvvm.commons.ui.extensions.observe
import co.androidbaseappkotlinmvvm.commons.views.ProgressBarDialog
import co.androidbaseappkotlinmvvm.dynamicfeatures.characterslist.databinding.FragmentMovieDetailBinding
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.R
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.detail.di.MovieDetailModule
import javax.inject.Inject

/**
 * View detail for selected movie, displaying extra info and with option to add it to favorite.
 *
 * @see BaseFragment
 */
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(
        layoutId = R.layout.fragment_movie_detail
    ) {

    @Inject
    lateinit var progressDialog: ProgressBarDialog

    private val args: MovieDetailFragmentArgs by navArgs()

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
        viewModel.loadCharacterDetail(args.characterId)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerMovieDetailComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .characterDetailModule(MovieDetailModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    // ============================================================================================
    //  Private observers methods
    // ============================================================================================

    /**
     * Observer view state change on [MovieDetailViewState].
     *
     * @param viewState State of character detail.
     */
    private fun onViewStateChange(viewState: MovieDetailViewState) {
        when (viewState) {
            is MovieDetailViewState.Loading ->
                progressDialog.show(R.string.movie_detail_dialog_loading_text)
            is MovieDetailViewState.Error ->
                progressDialog.dismissWithErrorMessage(R.string.movie_detail_dialog_error_text)
            is MovieDetailViewState.AddedToFavorite ->
                Snackbar.make(
                    requireView(),
                    R.string.movie_detail_added_to_favorite_message,
                    Snackbar.LENGTH_LONG
                ).show()
            is MovieDetailViewState.Dismiss ->
                findNavController().navigateUp()
            else -> progressDialog.dismiss()
        }
    }
}
