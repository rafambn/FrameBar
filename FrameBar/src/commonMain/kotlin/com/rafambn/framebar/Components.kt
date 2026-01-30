package com.rafambn.framebar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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
    Spacer(
        modifier
            .size(pointer.size.width, pointer.size.height + pointer.topOffset)
            .drawBehind {
                pointer.bitmap?.let { bitmap ->
                    drawImage(
                        image = bitmap,
                        dstOffset = IntOffset(0, pointer.topOffset.toPx().toInt()),
                        dstSize = IntSize(pointer.size.width.toPx().toInt(), pointer.size.height.toPx().toInt()) //TODO lots of conversion search way to improve
                    )
                } ?: run {
                    drawRect(
                        color = pointer.color,
                        topLeft = Offset(
                            0F,
                            pointer.topOffset.toPx()
                        ),
                        size = DpSize(pointer.size.width, pointer.size.height).toSize()
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
    val offsets = mutableListOf<Dp>()
    var tempOffset = 0.dp
    markersList.forEach {
        offsets.add(tempOffset)
        tempOffset += it.size.width
    }
    Spacer(
        modifier
            .size(
                markersList.sumOf { it.size.width.value.toInt() }.dp,
                markersList.maxOf { it.size.height + it.topOffset }
            )
            .drawBehind {
                markersList.forEachIndexed { index, marker ->
                    marker.bitmap?.let { bitmap ->
                        drawImage(
                            image = bitmap,
                            dstOffset = IntOffset(offsets[index].toPx().toInt(), marker.topOffset.toPx().toInt()), //TODO lots of conversion search way to improve
                            dstSize = IntSize(marker.size.width.toPx().toInt(), marker.size.height.toPx().toInt())
                        )
                    } ?: run {
                        drawRect(
                            color = marker.color,
                            topLeft = Offset(offsets[index].toPx(), marker.topOffset.toPx()),
                            size = DpSize(marker.size.width, marker.size.height).toSize()
                        )
                    }
                }
            }
    )
}


