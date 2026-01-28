package com.rafambn.framebar

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.rafambn.framebar.enums.CoercePointer
import com.rafambn.framebar.enums.ComponentType
import com.rafambn.framebar.enums.Movement
import com.rafambn.framebar.enums.PointerSelection
import kotlin.math.floor
import kotlin.math.max

/**
 * Continuous FrameBar that maps a draggable pointer across the marker track and reports a float value.
 *
 * @param modifier Modifier for sizing, positioning, and interaction decorations.
 * @param pointerSelection Alignment of the pointer relative to the track (left, center, right).
 * @param coercedPointer Whether pointer width is considered in the draggable range.
 * @param pointer Marker used as the draggable pointer visuals.
 * @param markers List of markers that compose the track visuals.
 * @param value Current value of the bar.
 * @param valueRange Optional range for the value mapping; when set, value is mapped to track width.
 * @param onValueChange Called when the value changes during drag.
 * @param onDragStarted Called when the drag gesture starts.
 * @param onDragStopped Called when the drag gesture ends.
 * @param enabled Whether dragging is enabled.
 * @param interactionSource Optional interaction source for drag/press states.
 */
@Composable
fun FrameBar(
    modifier: Modifier = Modifier,
    pointerSelection: PointerSelection = PointerSelection.CENTER,
    coercedPointer: CoercePointer = CoercePointer.NOT_COERCED,
    pointer: Marker,
    markers: List<Marker>,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0F..1F,
    onValueChange: (Float) -> Unit,
    onDragStarted: (() -> Unit)? = null,
    onDragStopped: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null
) {
    FrameBarImpl(
        modifier = modifier,
        movement = Movement.CONTINUOUS,
        pointerSelection = pointerSelection,
        coercedPointer = coercedPointer,
        pointer = pointer,
        markers = markers,
        value = value,
        onValueChange = onValueChange,
        onDragStarted = onDragStarted,
        onDragStopped = onDragStopped,
        valueRange = valueRange,
        enabled = enabled,
        interactionSource = interactionSource,
    )
}

/**
 * Discrete FrameBar that snaps the pointer to marker positions and reports the selected index.
 *
 * @param modifier Modifier for sizing, positioning, and interaction decorations.
 * @param pointerSelection Alignment of the pointer relative to the track (left, center, right).
 * @param pointer Marker used as the draggable pointer visuals.
 * @param markers List of markers that compose the track visuals.
 * @param index Currently selected marker index.
 * @param onIndexChange Called when the selected index changes during drag.
 * @param onDragStarted Called when the drag gesture starts.
 * @param onDragStopped Called when the drag gesture ends.
 * @param enabled Whether dragging is enabled.
 * @param interactionSource Optional interaction source for drag/press states.
 */
@Composable
fun FrameBar(
    modifier: Modifier = Modifier,
    pointerSelection: PointerSelection = PointerSelection.CENTER,
    pointer: Marker,
    markers: List<Marker>,
    index: Int,
    onIndexChange: (Int) -> Unit,
    onDragStarted: (() -> Unit)? = null,
    onDragStopped: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null
) {
    FrameBarImpl(
        modifier = modifier,
        movement = Movement.DISCRETE,
        pointerSelection = pointerSelection,
        coercedPointer = CoercePointer.NOT_COERCED,
        pointer = pointer,
        markers = markers,
        value = index.toFloat(),
        onValueChange = { newValue ->
            onIndexChange(newValue.toInt())
        },
        onDragStarted = onDragStarted,
        onDragStopped = onDragStopped,
        enabled = enabled,
        interactionSource = interactionSource,
        valueRange = 0F..markers.size.toFloat()
    )
}

@Composable
private fun FrameBarImpl(
    modifier: Modifier = Modifier,
    movement: Movement = Movement.CONTINUOUS,
    pointerSelection: PointerSelection = PointerSelection.CENTER,
    coercedPointer: CoercePointer = CoercePointer.NOT_COERCED,
    pointer: Marker,
    markers: List<Marker>,
    value: Float,
    onValueChange: (Float) -> Unit,
    onDragStarted: (() -> Unit)? = null,
    onDragStopped: (() -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0F..1F,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null
) {
    val density = LocalDensity.current
    val valueState by rememberUpdatedState(value.coerceIn(valueRange))

    val offsets = remember(markers.toList(), density) {
        mutableListOf<Float>().apply {
            clear()
            var tempOffset = 0F
            markers.forEach {
                add(tempOffset)
                tempOffset += with(density) { it.size.width.toPx() }
            }
        }.toList()
    }

    val pointerWidthPx = remember(pointer.hashCode()) { with(density) { pointer.size.width.toPx() } }
    val trackWidthPx = remember(markers.toList()) {
        with(density) {
            markers.sumOf { it.size.width.toPx().toInt() }
        } - if (coercedPointer == CoercePointer.COERCED) pointerWidthPx else 0F
    }

    var acumulatedDelta by remember { mutableFloatStateOf(0f) }

    val draggableState = remember(markers.toList()) {
        DraggableState { delta ->
            when (movement) {
                Movement.DISCRETE -> {
                    acumulatedDelta += delta
                    val offset = findOffsetTroughIndex(valueState, markers)
                    val index = findIndexTroughOffset(offset - acumulatedDelta, offsets)
                    val newIndex = index.coerceIn(valueRange)
                    if (newIndex != valueState) {
                        onValueChange(newIndex)
                        acumulatedDelta = 0f
                    }
                }

                Movement.CONTINUOUS -> onValueChange(
                    valueState - convertRange(delta, 0F..trackWidthPx, valueRange)
                )
            }
        }
    }

    Layout(
        {
            Box(modifier = Modifier.layoutId(ComponentType.POINTER)) { Pointer(pointer = pointer) }
            Box(modifier = Modifier.layoutId(ComponentType.TRACK)) { Markers(markersList = markers) }
        },
        modifier = modifier
            .wrapContentSize()
            .requiredSizeIn(
                minWidth = markers.sumOf { it.size.width.value.toInt() }.dp,
                minHeight = maxOf(
                    markers.maxOf { it.size.height + it.topOffset },
                    pointer.size.height + pointer.topOffset
                )
            )
            .clipToBounds()
            .let { modifier1 ->
                if (enabled) modifier1.draggable(
                    interactionSource = interactionSource,
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                    onDragStarted = {
                        onDragStarted?.invoke()
                    },
                    onDragStopped = {
                        onDragStopped?.invoke()
                    })
                else modifier1
            }
            .focusable(enabled)
    ) { measures, constraints ->

        val pointerPlaceable = measures.first {
            it.layoutId == ComponentType.POINTER
        }.measure(constraints)

        val markersPlaceable = measures.first {
            it.layoutId == ComponentType.TRACK
        }.measure(constraints)

        //Some variable to improve undestanding
        val progressBarWidth = markersPlaceable.width
        val progressBarHeight = max(markersPlaceable.height, pointerPlaceable.height)
        val halfPointerWidth = floor(pointerWidthPx / 2).toInt()
        val halfProgressBarWidth = progressBarWidth / 2

        //Variable to define the placement of the pointer with its center align with the center of the layout
        val pointerOffsetX = halfProgressBarWidth - halfPointerWidth
        val pointerOffsetY = 0

        //This variable determines the placement of the markers. It aligns the left edge of the markers with the left edge of the pointer. Depending on the selection type
        // of the pointer and if the pointer is coerced, it then shifts the markers to the right.
        val markersOffsetX = pointerOffsetX + if (coercedPointer == CoercePointer.NOT_COERCED)
            pointerSelectionShift(pointerSelection, halfPointerWidth, pointerWidthPx.toInt())
        else
            0
        val markersOffsetY = 0

        layout(
            progressBarWidth,
            progressBarHeight
        ) {
            //It ensures that the movement is limited to the maximum width of the markers. If the pointer is in a coerced state, the width of the pointer is subtracted
            // from the total movement.
            val coercedValue = if (movement == Movement.CONTINUOUS) {
                convertRange(
                    value.coerceIn(valueRange.start, valueRange.endInclusive),
                    valueRange,
                    0F..trackWidthPx
                ).toInt()
            } else
                findOffsetTroughIndex(value, markers).dp.toPx().toInt()

            markersPlaceable.placeRelative(
                markersOffsetX - coercedValue,
                markersOffsetY
            )
            pointerPlaceable.placeRelative(
                pointerOffsetX,
                pointerOffsetY
            )
        }
    }
}

private fun findIndexTroughOffset(offset: Float, listOffset: List<Float>): Float {
    val index = listOffset.indexOfLast { offset >= it }
    return if (index != -1) index.toFloat() else 0F
}

private fun findOffsetTroughIndex(selectedIndex: Float, markers: List<Marker>): Float {
    var starOffset = 0F
    markers.forEachIndexed { index, marker ->
        if (selectedIndex == index.toFloat()) {
            starOffset += marker.size.width.value / 2
            return starOffset
        } else starOffset += marker.size.width.value
    }
    return starOffset
}

private fun pointerSelectionShift(pointerSelection: PointerSelection, halfPointerWidth: Int, pointerWidth: Int): Int {
    return when (pointerSelection) {
        PointerSelection.LEFT -> 0
        PointerSelection.CENTER -> halfPointerWidth
        PointerSelection.RIGHT -> pointerWidth
    }
}

private fun convertRange(
    value: Float,
    originalRange: ClosedFloatingPointRange<Float>,
    targetRange: ClosedFloatingPointRange<Float>
): Float {
    return (value - originalRange.start) / (originalRange.endInclusive - originalRange.start) * (targetRange.endInclusive - targetRange.start) + targetRange.start
}
