package com.rafambn.framebar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
internal fun Pointer(
    modifier: Modifier = Modifier,
    pointer: Marker
) {
    Spacer(
        modifier
            .size(pointer.size)
            .drawBehind {
                pointer.bitmap?.let { bitmap ->
                    drawImage(image = bitmap)
                } ?: run {
                    drawRect(color = pointer.color)
                }
            }
    )
}

@Composable
fun Markers(
    modifier: Modifier = Modifier,
    markersList: List<Marker>
) {
    val density = LocalDensity.current

    val offsetsPx = remember(markersList, density) {
        var tempOffset = 0f
        markersList.map { marker ->
            val current = tempOffset
            tempOffset += with(density) { marker.size.width.toPx() }
            current
        }
    }

    val sizesPx = remember(markersList, density) {
        markersList.map { marker ->
            with(density) {
                Size(marker.size.width.toPx(), marker.size.height.toPx())
            }
        }
    }

    val topOffsetsPx = remember(markersList, density) {
        markersList.map { with(density) { it.topOffset.toPx() } }
    }

    val totalWidth = remember(markersList) { markersList.sumOf { it.size.width.value.toDouble() }.dp }
    val minHeight = remember(markersList) {
        markersList.maxOf { it.size.height + it.topOffset }
    }

    Spacer(
        modifier
            .size(totalWidth, minHeight)
            .drawBehind {
                markersList.forEachIndexed { index, marker ->
                    marker.bitmap?.let { bitmap ->
                        drawImage(
                            image = bitmap,
                            dstOffset = IntOffset(offsetsPx[index].toInt(), topOffsetsPx[index].toInt()),
                            dstSize = IntSize(sizesPx[index].width.toInt(), sizesPx[index].height.toInt())
                        )
                    } ?: run {
                        drawRect(
                            color = marker.color,
                            topLeft = Offset(offsetsPx[index], topOffsetsPx[index]),
                            size = sizesPx[index]
                        )
                    }
                }
            }
    )
}


