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

package co.androidbaseappkotlinmvvm.commons.ui.bindings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.api.load
import co.androidbaseappkotlinmvvm.commons.ui.R
import coil.transform.CircleCropTransformation
import kotlin.random.Random

/**
 * Set image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 * @param placeholderId Drawable resource identifier to set while downloading image.
 */
@BindingAdapter("imageUrl", "imagePlaceholder", requireAll = false)
fun ImageView.imageUrl(url: String?, @DrawableRes placeholderId: Int?) {
    load(url) {
        crossfade(true)
        transformations(CircleCropTransformation)
        placeholder(placeholderId?.let {
            ContextCompat.getDrawable(context, it)
        } ?: run {
            val placeholdersColors = resources.getStringArray(R.array.placeholders)
            val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
            ColorDrawable(Color.parseColor(placeholderColor))
        })
    }
}
