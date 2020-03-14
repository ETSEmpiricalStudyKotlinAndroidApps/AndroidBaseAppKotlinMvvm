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

package dependencies

/**
 * Project annotation processor dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object AnnotationProcessorsDependencies {
    const val DAGGER = "com.google.dagger:dagger-compiler:${BuildDependenciesVersions.DAGGER}"
    const val DATABINDING = "com.android.databinding:compiler:${BuildDependenciesVersions.DATABINDING}"
    const val ROOM = "androidx.room:room-compiler:${BuildDependenciesVersions.ROOM}"
    const val AUTO_SERVICE = "com.google.auto.service:auto-service:${BuildDependenciesVersions.AUTO_SERVICE}"
}