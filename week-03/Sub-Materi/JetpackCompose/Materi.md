
# Introduction to Jetpack Compose

## What is Jetpack Compose?
Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android with less code, powerful tools, and intuitive Kotlin APIs. Jetpack Compose allows developers to create UI elements with declarative Kotlin code, avoiding the complexity of XML layouts.

### Key Features
- **Declarative UI**: Build UIs by describing how they should look and behave in a stateless manner.
- **Less Code**: Write less code with fewer bugs, using Kotlin’s expressive language features.
- **Recomposable**: Automatically recompose the UI when the state changes, ensuring your app always stays in sync.
- **Interoperable**: You can combine Jetpack Compose with existing UI frameworks, like Views, seamlessly.
- **Material Design Components**: Compose comes with a set of pre-built Material Design components.

## System Requirements
To use Jetpack Compose, ensure your Android Studio setup is updated to the following:

- Android Studio Arctic Fox (2020.3.1) or newer.
- Kotlin version 1.5.21 or higher.
- Android Gradle Plugin (AGP) version 7.0.0 or higher.

## Setting Up Jetpack Compose in Android Studio
1. Make sure you're using the latest version of Android Studio (Artic Fox or higher).
2. When creating a new project, choose the **Jetpack Compose Activity** template from the setup wizard.
3. Jetpack Compose will be automatically set up, and necessary dependencies will be included in your `build.gradle` files.

To manually enable Compose in an existing project:
1. Add the following dependencies to your `build.gradle`:
```gradle
dependencies {
    implementation "androidx.compose.ui:ui:1.0.5"
    implementation "androidx.compose.material:material:1.0.5"
    implementation "androidx.compose.ui:ui-tooling-preview:1.0.5"
}
```
2. Ensure that the `composeOptions` block is added to enable Compose:
```gradle
android {
    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion '1.0.5'
    }
}
```

## Creating Your First Jetpack Compose UI
Jetpack Compose follows a declarative programming model where UI components are functions.

### Example: Simple Text UI
```kotlin
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}

@Preview
@Composable
fun PreviewGreeting() {
    Greeting("Jetpack Compose")
}
```

This simple example displays "Hello, Jetpack Compose!" on the screen. The `@Composable` annotation tells the compiler that this function defines part of the UI.

## Key Concepts in Jetpack Compose
- **Composable Functions**: Functions annotated with `@Composable` are the building blocks of UI in Compose.
- **Recomposition**: Compose automatically updates the UI when the data (state) changes.
- **State Management**: Use `State` and `MutableState` to hold and manage UI state in a reactive way.
- **Modifiers**: Modifiers allow you to customize the appearance and behavior of Compose elements, such as padding, size, and alignment.

### Example: State Management with `State`
```kotlin
import androidx.compose.runtime.*
import androidx.compose.material.*

@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }

    Button(onClick = { count++ }) {
        Text("Clicked $count times")
    }
}
```

In this example, `mutableStateOf` keeps track of the `count` value, and Compose will automatically recompose the button's text when the state changes.

## Previewing UI in Android Studio
Jetpack Compose comes with a powerful preview feature that lets you see your UI without needing to run the app on a device or emulator.

To use preview:
1. Annotate your composable function with `@Preview`.
2. Click the "Split" view in Android Studio to see a live preview of your UI.

## Integration with Existing Views
Jetpack Compose can be integrated with existing Android Views. You can include Compose UIs in XML layouts or vice versa using `ComposeView` and `AndroidView` functions.

### Example: Compose in XML
```xml
<androidx.compose.ui.platform.ComposeView
    android:id="@+id/compose_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

Then in your Activity or Fragment:
```kotlin
val composeView: ComposeView = findViewById(R.id.compose_view)
composeView.setContent {
    Greeting("Compose in XML")
}
```

## Conclusion
Jetpack Compose modernizes Android UI development by allowing developers to write less code and focus on building dynamic UIs using Kotlin. By adopting Compose, you can improve productivity and create more maintainable code. It’s compatible with existing View-based UIs, so you can adopt Compose at your own pace.

[BACK](../../Materi.md) 
