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

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val SPLASH_SCREEN_TIMEOUT = 3000L

/**
 * View model responsible for preparing and managing the data for [SplashScreenFragment].
 *
 * @see ViewModel
 */
class SplashScreenViewModel : ViewModel() {

    private val _state = MutableLiveData<SplashScreenViewState>()
    val state: LiveData<SplashScreenViewState>
        get() = _state

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    fun startAnimation() {
        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            _state.postValue(SplashScreenViewState.AnimationEnded)
        }, SPLASH_SCREEN_TIMEOUT)
    }
}
