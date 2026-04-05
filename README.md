# AIChatBot

A Kotlin Multiplatform chat application built with Compose Multiplatform. The project targets Android, iOS, Web, and Desktop (JVM) from a shared codebase.

## Version badges

These badges reflect the key versions declared in `gradle/libs.versions.toml`.

![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?logo=kotlin&logoColor=white)
![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.10.3-4285F4?logo=jetpackcompose&logoColor=white)
![Ktor](https://img.shields.io/badge/Ktor-3.4.2-087CFA?logo=ktor&logoColor=white)
![Koin](https://img.shields.io/badge/Koin-4.2.0-7A3FF0?logo=koin&logoColor=white)
![Serialization](https://img.shields.io/badge/Kotlinx%20Serialization-1.10.0-6C6CFF?logo=kotlin&logoColor=white)
![Navigation](https://img.shields.io/badge/Navigation-2.9.2-0F9D58?logo=android&logoColor=white)
![Coroutines](https://img.shields.io/badge/Kotlinx%20Coroutines-1.10.2-0095D5?logo=kotlin&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-1.10.0--alpha05-6750A4?logo=materialdesign&logoColor=white)
![Android Gradle Plugin](https://img.shields.io/badge/Android%20Gradle%20Plugin-8.11.2-3DDC84?logo=androidstudio&logoColor=white)
![Android API](https://img.shields.io/badge/Android%20Compile%20SDK-36-3DDC84?logo=android&logoColor=white)
![Desktop JVM](https://img.shields.io/badge/Desktop%20JVM-supported-2D2D2D?logo=java&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-supported-000000?logo=apple&logoColor=white)
![Web](https://img.shields.io/badge/Web-supported-2F80ED?logo=webcomponents.org&logoColor=white)

## Project overview

The app uses a shared Compose UI and a layered architecture:

- `presentation` handles screens, UI state, and ViewModels.
- `domain` contains business contracts, use cases, and platform-independent models.
- `data` contains the remote repository, DTOs, and mappers that talk to the Gemini API.
- `di` contains the dependency injection setup using Koin.
- Platform source sets provide the target-specific entry points and runtime behavior.

## Code structure

### `composeApp/src/commonMain`

This is the shared source set used by every platform.

- `App.kt` starts the application UI and Koin dependency injection.
- `presentation/screens` contains the user-facing Compose screens.
- `presentation/viewmodel` contains shared ViewModels.
- `presentation/states` contains UI state holders.
- `domain/common` contains result wrappers such as `ResultState`.
- `domain/model` contains clean domain models without serialization annotations.
- `domain/repository` defines repository contracts.
- `domain/useCases` contains business actions such as `AskQuestionWithPromptUseCase`.
- `data/remote/dataClass` contains Gemini API DTOs.
- `data/remote/mapper` converts between domain models and remote DTOs.
- `data/remote/repository` performs the network request using Ktor.
- `di/modules` defines the shared Koin module and HTTP client setup.

### Platform-specific source sets

- `androidMain` contains the Android activity and Android Ktor engine setup.
- `iosMain` contains the iOS `ComposeUIViewController` bridge and iOS platform implementation.
- `jvmMain` contains the desktop entry point and JVM platform implementation.
- `jsMain` contains the Kotlin/JS platform implementation.
- `wasmJsMain` contains the Kotlin/Wasm platform implementation.
- `webMain` contains the browser entry point.

### `iosApp/iosApp`

This directory contains the SwiftUI iOS host application.

- `ContentView.swift` embeds the shared Compose UI inside SwiftUI.
- `iOSApp.swift` is the SwiftUI app entry point.

## Required Gemini API key setup

Before running the app, you must add your own Gemini API key locally.

Create this file:

`composeApp/src/commonMain/kotlin/com/nutrino/aichatbot/Constants/GeminiConstants.kt`

Use this package name exactly:

`package com.nutrino.aichatbot.Constants`

Inside that file, create this object exactly:

- `object GeminiConstants`
- `const val GEMINI_API_KEY = "YOUR_GEMINI_API_KEY_HERE"`

Important notes:

- Replace `YOUR_GEMINI_API_KEY_HERE` with your own private Gemini API key.
- Do not commit the real key to version control.
- Keep the file name as `GeminiConstants.kt` and the object name as `GeminiConstants` so the repository code can read it correctly.

## Build and run

### Android

```shell
./gradlew :composeApp:assembleDebug
```

On Windows:

```powershell
.\gradlew.bat :composeApp:assembleDebug
```

### Desktop (JVM)

```shell
./gradlew :composeApp:run
```

On Windows:

```powershell
.\gradlew.bat :composeApp:run
```

### Web

Wasm target:

```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

Windows:

```powershell
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

JS target:

```shell
./gradlew :composeApp:jsBrowserDevelopmentRun
```

Windows:

```powershell
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

### iOS

Open the `iosApp` folder in Xcode and run the app from there.

## External libraries used

The project uses a version catalog, so the dependency versions live in `gradle/libs.versions.toml` and the actual
`implementation(...)` calls live in `composeApp/build.gradle.kts`.

### Ktor

Use Ktor for networking and API calls.

Version catalog entries in `gradle/libs.versions.toml`:

```toml
 [versions]
 ktor = "3.4.2"

ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
```

Where to place them in `composeApp/build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}

androidMain.dependencies {
    implementation(libs.ktor.client.okhttp)
}

jvmMain.dependencies {
    implementation(libs.ktor.client.okhttp)
}

iosMain.dependencies {
    implementation(libs.ktor.client.darwin)
}

jsMain.dependencies {
    implementation(libs.ktor.client.core)
}

wasmJsMain.dependencies {
    implementation(libs.ktor.client.core)
}
```

### Koin

Use Koin for dependency injection.

Version catalog entries in `gradle/libs.versions.toml`:

```toml
 [versions]
 koin = "4.2.0"
 koinCompose = "4.2.0"
 koinComposeViewModel = "4.2.0"

koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koinCompose" }
koin-compose-viewmodel = { module = "io.insert-koin:koin-compose-viewmodel", version.ref = "koinComposeViewModel" }
```

Where to place them in `composeApp/build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
}
```

### Navigation

Use Navigation Compose for app navigation.

Version catalog entry in `gradle/libs.versions.toml`:

```toml
 [versions]
navigationCompose = "2.9.2"

navigation-compose = { module = "org.jetbrains.androidx.navigation:navigation-compose", version.ref = "navigationCompose" }
```

Where to place it in `composeApp/build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation(libs.navigation.compose)
}
```

### Other shared libraries used by the app

Kotlin Serialization and shared Compose dependencies are also declared in the catalog and added from the shared source set.

```toml
 [versions]
 kotlinx-serialization = "1.10.0"
 composeMultiplatform = "1.10.3"
 material3 = "1.10.0-alpha05"

kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }
compose-runtime = { module = "org.jetbrains.compose.runtime:runtime", version.ref = "composeMultiplatform" }
compose-foundation = { module = "org.jetbrains.compose.foundation:foundation", version.ref = "composeMultiplatform" }
compose-material3 = { module = "org.jetbrains.compose.material3:material3", version.ref = "material3" }
compose-ui = { module = "org.jetbrains.compose.ui:ui", version.ref = "composeMultiplatform" }
compose-components-resources = { module = "org.jetbrains.compose.components:components-resources", version.ref = "composeMultiplatform" }
compose-uiToolingPreview = { module = "org.jetbrains.compose.ui:ui-tooling-preview", version.ref = "composeMultiplatform" }
```

```kotlin
commonMain.dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.components.resources)
    implementation(libs.compose.uiToolingPreview)
}
```

For desktop coroutine support, the project also uses:

Version catalog entry in `gradle/libs.versions.toml`:

- `kotlinx-coroutines = "1.10.2"`
- `kotlinx-coroutinesSwing = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-swing", version.ref = "kotlinx-coroutines" }`

Where to place it in `composeApp/build.gradle.kts`:

```kotlin
jvmMain.dependencies {
    implementation(libs.kotlinx.coroutinesSwing)
}
```

### Where to place new libraries

If you add a new dependency in the future:

1. Add its version and alias in `gradle/libs.versions.toml`.
2. Add the matching `implementation(libs.<alias>)` line in the correct source set inside `composeApp/build.gradle.kts`.
3. Keep private values such as API keys out of Gradle files and source control.

## Notes

- The shared UI uses a dark theme focused on a Gemini-style visual palette.
- The repository, use case, and ViewModel layers are separated so the app stays maintainable across platforms.
- Platform-specific logic is isolated in source-set files to keep the common code clean.

## References

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [Kotlin/Wasm](https://kotl.in/wasm/)
