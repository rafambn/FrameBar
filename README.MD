# FrameBar

[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-blue.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-7E52F0.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Maven Central](https://img.shields.io/maven-central/v/com.rafambn/framebar)](https://search.maven.org/search?q=g:com.rafambn%20AND%20a:framebar)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A customizable Kotlin Multiplatform seekbar component for Compose Multiplatform applications. Perfect for media players, video editing tools, or any scenario requiring precise position control within a frame-based timeline.

## ✨ Features

- 🎯 **Precise Control** - Support for both continuous (float-based) and discrete (index-based) navigation
- 🎨 **Fully Customizable** - Custom colors, sizes, images, and positioning for markers and pointers
- 📱 **Multiplatform** - Works on Android, iOS, Desktop, Web, and WASM
- 🎭 **Flexible Selection** - Choose from left, center, or right pointer selection modes
- 🔒 **Coercion Support** - Optional pointer coercion for precise frame alignment
- ⚡ **Lightweight** - Minimal dependencies and optimized performance
- 🎮 **Touch & Mouse** - Full support for both touch and mouse input

## 📦 Installation

Add the dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.rafambn:FrameBar:1.0.0")
}
```

## 🚀 Quick Start

### Basic Usage

```kotlin
import com.rafambn.framebar.FrameBar
import com.rafambn.framebar.Marker
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun MyVideoPlayer() {
    val currentPosition = remember { mutableStateOf(0f) }
    
    // Create your markers
    val markers = listOf(
        Marker(width = 10.dp, color = Color.Gray),
        Marker(width = 10.dp, color = Color.Gray, topOffset = 50.dp),
        Marker(width = 10.dp, color = Color.Gray),
        // ... more markers
    )
    
    // Create your pointer
    val pointer = Marker(
        width = 10.dp,
        height = 40.dp,
        color = Color.Yellow,
        topOffset = 5.dp
    )
    
    FrameBar(
        pointer = pointer,
        markers = markers,
        value = currentPosition.value,
        onValueChange = { newPosition ->
            currentPosition.value = newPosition
            // Handle position change (e.g., seek video)
        }
    )
}
```

### Discrete Index-Based Navigation

```kotlin
val currentFrame = remember { mutableStateOf(0) }

FrameBar(
    pointer = pointer,
    markers = frameMarkers,
    index = currentFrame.value,
    onIndexChange = { newFrame ->
        currentFrame.value = newFrame
        // Jump to specific frame
    }
)
```

## 🎨 Customization

### Marker Configuration

```kotlin
// Colored marker
val coloredMarker = Marker(
    width = 15.dp,
    height = 20.dp,
    color = Color.Blue,
    topOffset = 10.dp
)

// Image marker
val imageMarker = Marker(
    width = 20.dp,
    height = 30.dp,
    bitmap = myImageBitmap,
    topOffset = 5.dp
)
```

### Pointer Selection Modes

```kotlin
import com.rafambn.framebar.enums.PointerSelection

FrameBar(
    pointerSelection = PointerSelection.LEFT,   // Left edge of pointer
    // or
    pointerSelection = PointerSelection.CENTER, // Center of pointer (default)
    // or
    pointerSelection = PointerSelection.RIGHT,  // Right edge of pointer
    // ... other parameters
)
```

### Coerced Pointer Mode

```kotlin
import com.rafambn.framebar.enums.CoercePointer

FrameBar(
    coercedPointer = CoercePointer.COERCED, // Pointer stays within bounds
    // or
    coercedPointer = CoercePointer.NOT_COERCED, // Default behavior
    // ... other parameters
)
```

## 🔧 Advanced Configuration

### Custom Value Range

```kotlin
FrameBar(
    value = currentPosition.value,
    valueRange = 0f..100f, // Map pixels to custom range
    onValueChange = { normalizedValue ->
        // Convert back to your application's values
        val actualPosition = normalizedValue * totalDurationMs
        seekTo(actualPosition)
    }
)
```

### Event Handling

```kotlin
FrameBar(
    value = currentPosition.value,
    onValueChange = { position ->
        // Continuous updates during drag
        updatePosition(position)
    },
    onValueChangeStarted = {
        // User started dragging
        showPreview()
    },
    onValueChangeFinished = {
        // User finished dragging
        hidePreview()
        commitPosition()
    }
)
```

## 🏗️ Architecture

FrameBar is built with a clean, modular architecture:

- **FrameSeekBar.kt**: Main component logic and layout calculations
- **Marker.kt**: Data class for defining markers and pointers
- **Components.kt**: Visual rendering components
- **Enums**: Configuration options for behavior

## 📱 Platform Support

| Platform | Supported |
|----------|-----------|
| Android  | ✅ |
| iOS      | ✅ |
| Desktop  | ✅ |
| Web      | ✅ |
| WASM     | ✅ |

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.