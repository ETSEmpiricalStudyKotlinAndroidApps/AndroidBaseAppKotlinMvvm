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

package co.androidbaseappkotlinmvvm.core.network.responses

import co.androidbaseappkotlinmvvm.core.annotations.OpenForTesting
import com.google.gson.annotations.SerializedName

/**
 * Generic base response for any type
 *
 * @param code The HTTP status code of the returned result.
 * @param message A more descriptive message of the failure call status.
 */
@OpenForTesting
open class BaseResponse(
    @SerializedName("status_code")
    val code: Int?,
    @SerializedName("status_message")
    val message: String?
)
