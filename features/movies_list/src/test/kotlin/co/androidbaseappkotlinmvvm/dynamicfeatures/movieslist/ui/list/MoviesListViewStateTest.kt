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

package co.androidbaseappkotlinmvvm.favorite.movieslist.ui.list

import org.junit.Assert
import org.junit.Test

class MoviesListViewStateTest {

    lateinit var state: MoviesListViewState

    @Test
    fun setStateAsRefreshing_ShouldBeSettled() {
        state = MoviesListViewState.Refreshing

        Assert.assertTrue(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isAddError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsLoaded_ShouldBeSettled() {
        state = MoviesListViewState.Loaded

        Assert.assertTrue(state.isLoaded())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isAddError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsLoading_ShouldBeSettled() {
        state = MoviesListViewState.Loading

        Assert.assertTrue(state.isLoading())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isAddError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsAddLoading_ShouldBeSettled() {
        state = MoviesListViewState.AddLoading

        Assert.assertTrue(state.isAddLoading())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isAddError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsEmpty_ShouldBeSettled() {
        state = MoviesListViewState.Empty

        Assert.assertTrue(state.isEmpty())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isAddError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsError_ShouldBeSettled() {
        state = MoviesListViewState.Error

        Assert.assertTrue(state.isError())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isAddError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsAddError_ShouldBeSettled() {
        state = MoviesListViewState.AddError

        Assert.assertTrue(state.isAddError())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun setStateAsNoMoreElements_ShouldBeSettled() {
        state = MoviesListViewState.NoMoreElements

        Assert.assertTrue(state.isNoMoreElements())
        Assert.assertFalse(state.isRefreshing())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isAddLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isAddError())
    }
}
