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

package co.androidbaseappkotlinmvvm.dynamicfeatures.splashscreen.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import co.androidbaseappkotlinmvvm.MovieApp
import co.androidbaseappkotlinmvvm.commons.ui.base.BaseFragment
import co.androidbaseappkotlinmvvm.commons.ui.extensions.observe
import co.androidbaseappkotlinmvvm.dynamicfeatures.splashscreen.R
import co.androidbaseappkotlinmvvm.dynamicfeatures.splashscreen.databinding.FragmentSplashScreenBinding
import co.androidbaseappkotlinmvvm.dynamicfeatures.splashscreen.di.DaggerSplashScreenComponent
import co.androidbaseappkotlinmvvm.dynamicfeatures.splashscreen.di.SplashScreenModule

/**
 * Splash screen view.
 *
 * @see BaseFragment
 */
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(
    layoutId = R.layout.fragment_splash_screen
) {
    /**
     * Called to set fullscreen before this fragment is shown.
     *
     * @param context The application context
     *
     * @see BaseFragment.onAttach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Called to remove fullscreen after this fragment is shown.
     *
     * @see BaseFragment.onDetach
     */
    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

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
        viewModel.startAnimation()
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerSplashScreenComponent
            .builder()
            .coreComponent(MovieApp.coreComponent(requireContext()))
            .splashScreenModule(SplashScreenModule(this))
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
    //  Private setups methods
    // ============================================================================================
    /**
     * Observer view state change on [SplashScreenViewModel].
     *
     * @param viewState State of splash screen.
     */
    private fun onViewStateChange(viewState: SplashScreenViewState) {
        when (viewState) {
            is SplashScreenViewState.AnimationEnded ->
                findNavController().navigate(
                    SplashScreenFragmentDirections.actionSplashscreenFragmentToHomeFragment()
                )
        }
    }
}
