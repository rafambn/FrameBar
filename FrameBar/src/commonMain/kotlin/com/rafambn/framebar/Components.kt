package com.rafambn.framebar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun Pointer(
    modifier: Modifier = Modifier,
    pointer: Marker = Marker(
        size = DpSize(5.dp, 40.dp),
        topOffset = 0.dp,
        color = Color.Yellow
    )
) {
    val density = LocalDensity.current
    val topOffsetPx = remember(pointer.topOffset, density) { with(density) { pointer.topOffset.toPx() } }
    val widthPx = remember(pointer.size.width, density) { with(density) { pointer.size.width.toPx() } }
    val heightPx = remember(pointer.size.height, density) { with(density) { pointer.size.height.toPx() } }

    Spacer(
        modifier
            .size(pointer.size.width, pointer.size.height + pointer.topOffset)
            .drawBehind {
                pointer.bitmap?.let { bitmap ->
                    drawImage(
                        image = bitmap,
                        dstOffset = IntOffset(0, topOffsetPx.toInt()),
                        dstSize = IntSize(widthPx.toInt(), heightPx.toInt())
                    )
                } ?: run {
                    drawRect(
                        color = pointer.color,
                        topLeft = Offset(0F, topOffsetPx),
                        size = Size(widthPx, heightPx)
                    )
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
    val maxHeight = remember(markersList) { markersList.maxOf { it.size.height + it.topOffset } }

    Spacer(
        modifier
            .size(totalWidth, maxHeight)
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


