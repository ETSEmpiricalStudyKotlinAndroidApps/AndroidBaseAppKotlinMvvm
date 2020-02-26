# AndroidBaseAppKotlinMvvm
Base reference for new Android projects (Kotlin&MVVM)

[![CircleCI](https://circleci.com/gh/tecruz/AndroidBaseAppKotlinMvvm/tree/master.svg?style=shield)](https://circleci.com/tecruz/AndroidBaseAppKotlinMvvm/tree/master)
[![codecov](https://codecov.io/gh/tecruz/AndroidBaseAppKotlinMvvm/branch/master/graph/badge.svg)](https://codecov.io/gh/tecruz/AndroidBaseAppKotlinMvvm)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/171640011f3749d19a8de24877237522)](https://app.codacy.com/app/tecruz/AndroidBaseAppKotlinMvvm?utm_source=github.com&utm_medium=referral&utm_content=tecruz/AndroidBaseAppKotlinMvvm&utm_campaign=badger)

![Alt text](screen.png?raw=true)

You need to supply API keys for the various services the app uses.

-   Movie Db: https://developers.themoviedb.org/3
-   Firebase: https://firebase.google.com/docs/crashlytics/get-started?platform=Android

When you obtain the keys, you can provide them to the app by putting the following in the `local.properties` project root file:

```properties
#Movie db API KEY
movie.api.key=<insert>

Firebase
Include generated json file on your project
```


Moreover, to sign your app for release you should generate an upload key and keystore following the [official documentation](https://developer.android.com/studio/publish/app-signing#sign-apk). Append the information used to generate it into `local.properties` project root file following the structure:

```properties
#Signing Config
signing.key.alias = <insert>
signing.key.password = <insert>
signing.store.file = <insert>
signing.store.password = <insert>
```

Libraries and tools included:

-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html) - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx) - major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [Benchmark](https://developer.android.com/studio/profile/benchmark.html) - handles warmup, measures your code performance, and outputs benchmarking results to the Android Studio console.
    -   [Data Binding](https://developer.android.com/topic/libraries/data-binding/) - allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Navigation](https://developer.android.com/guide/navigation/) - helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [Paging](https://developer.android.com/topic/libraries/architecture/paging/) - helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources.
    -   [Room](https://developer.android.com/topic/libraries/architecture/room) - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - managing background threads with simplified code and reducing needs for callbacks.
-   [Dagger2](https://dagger.dev/) - dependency injector for replacement all FactoryFactory classes.
-   [Retrofit](https://square.github.io/retrofit/) - type-safe HTTP client.
-   [Coil](https://github.com/coil-kt/coil) - image loading library for Android backed by Kotlin Coroutines.
-   [Moshi](https://github.com/square/moshi) - makes it easy to parse JSON into Kotlin objects.
-   [Timber](https://github.com/JakeWharton/timber) - a logger with a small, extensible API which provides utility on top of Android's normal Log class.
-   [Stetho](http://facebook.github.io/stetho/) - debug bridge for applications via Chrome Developer Tools.

### Test dependencies

-   [UIAutomator](https://developer.android.com/training/testing/ui-automator) - a UI testing framework suitable for cross-app functional UI testing across system and installed apps.
-   [Espresso](https://developer.android.com/training/testing/espresso) - to write concise, beautiful, and reliable Android UI tests
-   [Robolectric](https://github.com/robolectric/robolectric) - industry-standard unit testing framework for Android.
-   [JUnit](https://github.com/junit-team/junit4) - a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
-   [Mockito](https://github.com/nhaarman/mockito-kotlin) - most popular Mocking framework for unit tests written in Java.
-   [Mockk](https://github.com/mockk/mockk) - provides DSL to mock behavior. Built from zero to fit Kotlin language.
-   [AndroidX](https://github.com/android/android-test) - the androidx test library provides an extensive framework for testing Android apps.

### Plugins

-   [Ktlint](https://github.com/pinterest/ktlint) - an anti-bikeshedding Kotlin linter with built-in formatter.
-   [Detekt](https://github.com/arturbosch/detekt) - a static code analysis tool for the Kotlin programming language.
-   [Spotless](https://github.com/diffplug/spotless) - a code formatter can do more than just find formatting errors.
-   [Versions](https://github.com/ben-manes/gradle-versions-plugin) - make easy to determine which dependencies have updates.
-   [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) - generates simple object and builder classes for type-safe navigation and access to any associated arguments.
-   [Jacoco](https://github.com/jacoco/jacoco) - code coverage library
