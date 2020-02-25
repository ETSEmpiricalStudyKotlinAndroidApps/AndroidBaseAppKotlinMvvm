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

package co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.ui.list.adapter.holders

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import co.androidbaseappkotlinmvvm.dynamicfeatures.movieslist.databinding.ListItemLoadingBinding
import co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list.adapter.holders.LoadingViewHolder
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class LoadingViewHolderTest {

    @MockK
    lateinit var view: View
    @MockK
    lateinit var layoutInflater: LayoutInflater
    @MockK(relaxed = true)
    lateinit var binding: ListItemLoadingBinding
    lateinit var viewHolder: LoadingViewHolder

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    @Ignore("Failed to create a Robolectric sandbox: Android SDK 29 requires Java 9 (have Java 8)")
    fun createViewHolder_ShouldInitializeCorrectly() {
        mockkStatic(ListItemLoadingBinding::class)
        every { (binding as ViewDataBinding).root } returns view
        every { ListItemLoadingBinding.inflate(layoutInflater) } returns binding

        viewHolder = LoadingViewHolder(layoutInflater)

        Assert.assertEquals(binding, viewHolder.binding)
    }
}
