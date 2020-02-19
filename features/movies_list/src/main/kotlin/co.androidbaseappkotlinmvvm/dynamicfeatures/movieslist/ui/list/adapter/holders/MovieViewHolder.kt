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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import co.androidbaseappkotlinmvvm.commons.ui.base.BaseViewHolder
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.databinding.ListItemMovieBinding
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.MoviesListViewModel
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.model.MovieItem

/**
 * Class describes movie view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class MovieViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemMovieBinding>(
    binding = ListItemMovieBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel Movie list view model.
     * @param item Movie list item.
     */
    fun bind(viewModel: MoviesListViewModel, item: MovieItem) {
        binding.viewModel = viewModel
        binding.movie = item
        binding.executePendingBindings()
    }
}
