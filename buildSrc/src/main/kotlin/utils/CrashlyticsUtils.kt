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

package utils

import org.gradle.api.Project

private const val GOOGLE_SERVICES_FILE_NAME = "app/google-services.json"

/**
 * Util to check `google-services.json` file it's included
 *
 * @param project the project reference
 */
fun checkGoogleServicesFile(project: Project) {
    val googleServicesFile = project.rootProject.file(GOOGLE_SERVICES_FILE_NAME)
    if (!googleServicesFile.exists()) {
        throw NoSuchFileException(
            file = googleServicesFile,
            reason = "You should include 'google-services.json' in app root project level. Visit 'https://firebase.google.com/docs/crashlytics/get-started?platform=Android' to obtain them."
        )
    }
}
