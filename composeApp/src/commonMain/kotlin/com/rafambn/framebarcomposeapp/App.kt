package com.rafambn.framebarcomposeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import com.rafambn.framebar.FrameBar
import com.rafambn.framebar.Marker
import com.rafambn.framebar.enums.CoercePointer
import com.rafambn.framebar.enums.PointerSelection
import com.rafambn.framebarcomposeapp.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "FrameBar Demonstrations",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 1. Pointer Selection - LEFT
            DemoSection(title = "1. Pointer Selection - LEFT") {
                var value by remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFFD32F2F), Color(0xFFC62828), Color(0xFFB71C1C),
                    Color(0xFFF57C00), Color(0xFFE65100), Color(0xFFFF6F00),
                    Color(0xFFFBC02D), Color(0xFFF57F17), Color(0xFFFFB300),
                    Color(0xFF388E3C), Color(0xFF1B5E20), Color(0xFF2E7D32)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.LEFT,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFF0D47A1)),
                    markers = markers,
                    value = value,
                    onValueChange = { value = it }
                )
                Text("Value: ${((value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
            }

            // 2. Pointer Selection - CENTER
            DemoSection(title = "2. Pointer Selection - CENTER") {
                val value = remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFF0D47A1), Color(0xFF1565C0), Color(0xFF1976D2),
                    Color(0xFF2196F3), Color(0xFF42A5F5), Color(0xFF64B5F6),
                    Color(0xFF00897B), Color(0xFF00796B), Color(0xFF00695C),
                    Color(0xFF009688), Color(0xFF26A69A), Color(0xFF4DB6AC)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFF00BFA5)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
            }

            // 3. Pointer Selection - RIGHT
            DemoSection(title = "3. Pointer Selection - RIGHT") {
                val value = remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFF7B1FA2), Color(0xFF6A1B9A), Color(0xFF4A148C),
                    Color(0xFF8E24AA), Color(0xFF9C27B0), Color(0xFFAB47BC),
                    Color(0xFFC2185B), Color(0xFFE91E63), Color(0xFFF06292),
                    Color(0xFF880E4F), Color(0xFFC2185B), Color(0xFFEC407A)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.RIGHT,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFF1A237E)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
            }

            // 4. Coerced Pointer - NOT_COERCED
            DemoSection(title = "4. Coerced Pointer - NOT_COERCED") {
                val value = remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFFE53935), Color(0xFFD32F2F), Color(0xFFC62828),
                    Color(0xFFB71C1C), Color(0xFFA5D6A7), Color(0xFF81C784),
                    Color(0xFF66BB6A), Color(0xFF4CAF50), Color(0xFF43A047),
                    Color(0xFF2E7D32)
                )
                val markers = colors.mapIndexed { index, color ->
                    val heights = listOf(20.dp, 30.dp, 20.dp, 25.dp, 15.dp, 35.dp, 22.dp, 28.dp, 18.dp, 32.dp)
                    val offsets = listOf(10.dp, 0.dp, 10.dp, 5.dp, 15.dp, 0.dp, 12.dp, 2.dp, 14.dp, 0.dp)
                    Marker(
                        size = DpSize(35.dp, heights[index % heights.size]),
                        topOffset = offsets[index % offsets.size],
                        color = color
                    )
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFF00E5FF)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
            }

            // 5. Coerced Pointer - COERCED
            DemoSection(title = "5. Coerced Pointer - COERCED") {
                val value = remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFFFF6F00), Color(0xFFE65100), Color(0xFFBF360C),
                    Color(0xFFF57C00), Color(0xFFFF9800), Color(0xFFFFB74D),
                    Color(0xFFFBC02D), Color(0xFFF9A825), Color(0xFFF57F17),
                    Color(0xFFFFB300), Color(0xFFFDD835), Color(0xFFFFEC00)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.Gray),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFFFFD600)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
            }

            // 6. Continuous Mode with Value Range
            DemoSection(title = "6. Continuous Mode with Value Range (0-100)") {
                val value = remember { mutableStateOf(50F) }
                val colors = listOf(
                    Color(0xFF9C27B0), Color(0xFF8E24AA), Color(0xFF7B1FA2),
                    Color(0xFF6A1B9A), Color(0xFF4A148C), Color(0xFFCE93D8),
                    Color(0xFFBA68C8), Color(0xFFAB47BC), Color(0xFF9C27B0),
                    Color(0xFF8E24AA), Color(0xFF7B1FA2), Color(0xFF6A1B9A),
                    Color(0xFF4A148C)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFF00BFA5)),
                    markers = markers,
                    value = value.value,
                    valueRange = 0F..100F,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}%", modifier = Modifier.padding(top = 8.dp))
            }

            // 7. Discrete Mode (Index-based)
            DemoSection(title = "7. Discrete Mode - Index Based Selection") {
                val index = remember { mutableStateOf(0) }
                val frameNames = listOf(
                    "Frame 1",
                    "Frame 2",
                    "Frame 3",
                    "Frame 4",
                    "Frame 5",
                    "Frame 6",
                    "Frame 7",
                    "Frame 8",
                    "Frame 9",
                    "Frame 10"
                )
                val colors = listOf(
                    Color(0xFF00BCD4), Color(0xFF0097A7), Color(0xFF006064),
                    Color(0xFF00ACC1), Color(0xFF0288D1), Color(0xFF0277BD),
                    Color(0xFF01579B), Color(0xFF00838F), Color(0xFF004D40),
                    Color(0xFF0D47A1)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFFFFD600)),
                    markers = markers,
                    index = index.value,
                    onIndexChange = { index.value = it }
                )
                Text(
                    "Selected: ${frameNames[index.value]} (Index: ${index.value})",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // 8. Drag Callbacks
            DemoSection(title = "8. Drag Callbacks - Drag Start/Stop Detection") {
                val value = remember { mutableStateOf(0F) }
                val isDragging = remember { mutableStateOf(false) }
                val dragStartCount = remember { mutableStateOf(0) }
                val dragStopCount = remember { mutableStateOf(0) }
                val colors = listOf(
                    Color(0xFF1976D2), Color(0xFF1565C0), Color(0xFF0D47A1),
                    Color(0xFF1E88E5), Color(0xFF2196F3), Color(0xFF42A5F5),
                    Color(0xFF64B5F6), Color(0xFF90CAF9), Color(0xFFBBDEFB),
                    Color(0xFFE3F2FD)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFFFFD600)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it },
                    onDragStarted = {
                        isDragging.value = true
                        dragStartCount.value++
                    },
                    onDragStopped = {
                        isDragging.value = false
                        dragStopCount.value++
                    }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
                Text("Dragging: ${isDragging.value}", modifier = Modifier.padding(top = 4.dp))
                Text("Drag Starts: ${dragStartCount.value}", modifier = Modifier.padding(top = 4.dp))
                Text("Drag Stops: ${dragStopCount.value}", modifier = Modifier.padding(top = 4.dp))
            }

            // 9. Disabled State
            DemoSection(title = "9. Disabled State") {
                val value = remember { mutableStateOf(0.5F) }
                val colors = listOf(
                    Color(0xFF757575), Color(0xFF616161), Color(0xFF424242),
                    Color(0xFF212121), Color(0xFF9E9E9E), Color(0xFF808080),
                    Color(0xFF616161), Color(0xFF424242), Color(0xFFBDBDBD),
                    Color(0xFFA1A1A1)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 30.dp, color = Color(0xFF424242)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it },
                    enabled = false
                )
                Text(
                    "Value: ${((value.value * 10).roundToInt() / 10f)} (Disabled - not interactive)",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // 10. Mixed Visual Styles
            DemoSection(title = "10. Mixed Visual Styles - Varied Heights & Offsets") {
                val value = remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFFD32F2F), Color(0xFFF44336), Color(0xFFE57373),
                    Color(0xFFFF7043), Color(0xFFFF5722), Color(0xFFFF6E40),
                    Color(0xFFFF9800), Color(0xFFFF6F00), Color(0xFFFDD835),
                    Color(0xFFFBC02D), Color(0xFFFFB300), Color(0xFFFFA000),
                    Color(0xFF4CAF50), Color(0xFF66BB6A), Color(0xFF2E7D32)
                )
                val markers = colors.mapIndexed { index, color ->
                    val heights = listOf(
                        15.dp,
                        40.dp,
                        25.dp,
                        30.dp,
                        22.dp,
                        40.dp,
                        25.dp,
                        35.dp,
                        20.dp,
                        38.dp,
                        28.dp,
                        33.dp,
                        18.dp,
                        42.dp,
                        26.dp
                    )
                    val offsets = listOf(
                        15.dp,
                        0.dp,
                        10.dp,
                        5.dp,
                        12.dp,
                        0.dp,
                        10.dp,
                        2.dp,
                        15.dp,
                        1.dp,
                        8.dp,
                        3.dp,
                        14.dp,
                        0.dp,
                        9.dp
                    )
                    Marker(
                        size = DpSize(30.dp, heights[index % heights.size]),
                        topOffset = offsets[index % offsets.size],
                        color = color
                    )
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(30.dp, 65.dp), topOffset = 0.dp, color = Color(0xFF00E5FF)),
                    markers = markers,
                    value = value.value,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
            }

            DemoSection(title = "11 - Set values") {
                val value = remember { mutableStateOf(0F) }
                val colors = listOf(
                    Color(0xFFFF5252), Color(0xFFFF6E40), Color(0xFFFFAB40),
                    Color(0xFFFDD835), Color(0xFFCDDC39), Color(0xFF9CCC65),
                    Color(0xFF66BB6A), Color(0xFF4CAF50), Color(0xFF00C853)
                )
                val markers = colors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(16.dp, 50.dp), topOffset = 25.dp, color = Color(0xFF2196F3)),
                    markers = markers,
                    value = value.value,
                    valueRange = 0F..100F,
                    onValueChange = { value.value = it }
                )

                Text(
                    "Value: ${((value.value * 10).roundToInt() / 10f)} / 100",
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                    Button(
                        onClick = { value.value = 0F },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        Text("Reset (0)")
                    }
                    Button(
                        onClick = { value.value = 50F },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text("Mid (50)")
                    }
                    Button(
                        onClick = { value.value = 100F },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        Text("End (100)")
                    }
                }
            }

            // 12. Animated Markers
            DemoSection(title = "12. Animated Markers - Changing Size & Color Every 1.5s") {
                val baseColors = listOf(
                    Color(0xFF1976D2), Color(0xFF1565C0), Color(0xFF0D47A1),
                    Color(0xFF1E88E5), Color(0xFF2196F3), Color(0xFF42A5F5),
                    Color(0xFF64B5F6), Color(0xFF90CAF9), Color(0xFFBBDEFB),
                    Color(0xFFE3F2FD)
                )
                val animatedColors = listOf(
                    Color(0xFFFF5252), Color(0xFFFF6E40), Color(0xFFFFAB40),
                    Color(0xFFFDD835), Color(0xFFFBC02D), Color(0xFF8BC34A)
                )
                val animatedSizes = listOf(
                    DpSize(20.dp, 30.dp),
                    DpSize(32.dp, 40.dp),
                    DpSize(40.dp, 50.dp),
                    DpSize(28.dp, 35.dp),
                    DpSize(35.dp, 45.dp)
                )
                val markers = remember { mutableStateOf(baseColors.map { color ->
                    Marker(size = DpSize(32.dp, 40.dp), topOffset = 0.dp, color = color)
                }) }
                val animatedMarkerIndex = remember { mutableStateOf(0) }
                val animationStep = remember { mutableStateOf(0) }
                val value = remember { mutableStateOf(0F) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        while (true) {
                            delay(1500)
                            animatedMarkerIndex.value = (animatedMarkerIndex.value + 1) % baseColors.size
                            animationStep.value = (animationStep.value + 1) % (animatedColors.size * animatedSizes.size)
                            val colorIndex = animationStep.value % animatedColors.size
                            val sizeIndex = (animationStep.value / animatedColors.size) % animatedSizes.size

                            val newMarkers = markers.value.mapIndexed { index, marker ->
                                if (index == animatedMarkerIndex.value) {
                                    Marker(
                                        size = animatedSizes[sizeIndex],
                                        topOffset = 0.dp,
                                        color = animatedColors[colorIndex]
                                    )
                                } else {
                                    marker
                                }
                            }
                            markers.value = newMarkers
                        }
                    }
                }

                FrameBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(horizontal = 16.dp),
                    pointerSelection = PointerSelection.CENTER,
                    coercedPointer = CoercePointer.NOT_COERCED,
                    pointer = Marker(size = DpSize(12.dp, 30.dp), topOffset = 50.dp, color = Color(0xFFFFD600)),
                    markers = markers.value,
                    value = value.value,
                    onValueChange = { value.value = it }
                )
                Text("Value: ${((value.value * 10).roundToInt() / 10f)}", modifier = Modifier.padding(top = 8.dp))
                Text("Animating marker at index: ${animatedMarkerIndex.value}", modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DemoSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}