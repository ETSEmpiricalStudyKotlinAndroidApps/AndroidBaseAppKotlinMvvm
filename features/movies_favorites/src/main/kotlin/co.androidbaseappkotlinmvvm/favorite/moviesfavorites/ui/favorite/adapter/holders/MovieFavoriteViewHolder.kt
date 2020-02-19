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

package co.androidbaseappkotlinmvvm.favorite.moviesfavorites.ui.favorite.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import co.androidbaseappkotlinmvvm.commons.ui.base.BaseViewHolder
import co.androidbaseappkotlinmvvm.core.database.moviefavorite.MovieFavorite
import co.androidbaseappkotlinmvvm.dynamicfeatures.charactersfavorites.databinding.ListItemMoviesFavoriteBinding


/**
 * Class describes movie favorite view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class MovieFavoriteViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemMoviesFavoriteBinding>(
    binding = ListItemMoviesFavoriteBinding.inflate(inflater)
) {
    /**
     * Bind view data binding variables.
     *
     * @param movieFavorite Character favorite to bind.
     */
    fun bind(movieFavorite: MovieFavorite) {
        binding.character = movieFavorite
        binding.executePendingBindings()
    }
}
