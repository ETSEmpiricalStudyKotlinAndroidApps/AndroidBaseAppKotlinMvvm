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

package commons

import BuildAndroidConfig
import BuildProductDimensions
import ProductFlavorDevelop
import ProductFlavorProduction
import ProductFlavorQA
import dependencies.Dependencies
import dependencies.TestDependencies
import dependencies.TestAndroidDependencies
import dependencies.AnnotationProcessorsDependencies
import BuildType.Companion.DEBUG
import BuildType.Companion.RELEASE

plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("kotlin-allopen")
    id("androidx.navigation.safeargs.kotlin")
    id("com.vanniktech.android.junit.jacoco")
    id("com.vanniktech.dependency.graph.generator")
}

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dataBinding {
        isEnabled = true
    }

    androidExtensions {
        isExperimental = true
    }

    buildTypes {
        getByName(RELEASE) {
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
        }

        getByName(DEBUG) {
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
    }

    flavorDimensions(BuildProductDimensions.ENVIRONMENT)
    productFlavors {
        ProductFlavorDevelop.libraryCreate(this)
        ProductFlavorQA.libraryCreate(this)
        ProductFlavorProduction.libraryCreate(this)
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }

    lintOptions {
        lintConfig = rootProject.file(".lint/config.xml")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}

junitJacoco {
    includeNoLocationClasses = true
}

dependencies {
    implementation(project(BuildModules.APP))
    implementation(project(BuildModules.CORE))
    implementation(project(BuildModules.Commons.UI))

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.COROUTINES)
    implementation(Dependencies.COROUTINES_ANDROID)
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.DAGGER)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.LOGGING)

    kapt(AnnotationProcessorsDependencies.DAGGER)
    kapt(AnnotationProcessorsDependencies.DATABINDING)
    kapt(AnnotationProcessorsDependencies.ROOM)

    testImplementation(project(BuildModules.Libraries.TEST_UTILS))

    testImplementation(TestDependencies.JUNIT)
    testImplementation(TestDependencies.MOCKITO)
    testImplementation(TestDependencies.MOCKK)
    testImplementation(TestDependencies.ASSERTJ)
    testImplementation(TestDependencies.ROOM)
    testImplementation(TestDependencies.CORE)
    testImplementation(TestDependencies.ARCH_CORE)
    testImplementation(TestDependencies.RULES)
    testImplementation(TestDependencies.RUNNER)
    testImplementation(TestDependencies.COROUTINES_TEST)
    testImplementation(TestDependencies.FRAGMENT_TEST)
    testImplementation(TestDependencies.EXT)
    testImplementation(TestDependencies.MOCK_WEB_SERVER)

    androidTestImplementation(TestAndroidDependencies.PLAY_CORE)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO)
    androidTestImplementation(TestAndroidDependencies.RUNNER)
    androidTestImplementation(TestAndroidDependencies.RULES)
    androidTestImplementation(TestAndroidDependencies.CORE)
    androidTestImplementation(TestAndroidDependencies.JUNIT)
    androidTestImplementation(TestAndroidDependencies.FRAGMENT_TEST)
    androidTestImplementation(TestAndroidDependencies.OBJENESIS)

    // - Exclude module for https://github.com/mockk/mockk/issues/281
    androidTestImplementation(TestAndroidDependencies.MOCKK) {
        exclude(module = "objenesis")
    }
}