package com.rafambn.framebar

import com.rafambn.framebar.enums.CoercePointer
import kotlin.math.roundToInt

internal fun markerStartOffsetsPx(markerWidthsPx: List<Float>): List<Float> {
    var offset = 0F
    return markerWidthsPx.map { widthPx ->
        val current = offset
        offset += widthPx
        current
    }
}

internal fun markerCenterOffsetForIndexPx(selectedIndex: Float, markerWidthsPx: List<Float>): Float {
    if (markerWidthsPx.isEmpty()) return 0F

    val targetIndex = selectedIndex.roundToInt().coerceIn(0, markerWidthsPx.lastIndex)
    return markerWidthsPx
        .take(targetIndex)
        .sum() + markerWidthsPx[targetIndex] / 2F
}

internal fun markerIndexForOffsetPx(offsetPx: Float, markerStartOffsetsPx: List<Float>): Float {
    val index = markerStartOffsetsPx.indexOfLast { offsetPx >= it }
    return if (index != -1) index.toFloat() else 0F
}

internal fun effectiveTrackWidthPx(
    markerWidthsPx: List<Float>,
    coercedPointer: CoercePointer,
    pointerWidthPx: Float
): Float {
    val markerWidthPx = markerWidthsPx.sum()
    val pointerAdjustmentPx = if (coercedPointer == CoercePointer.COERCED) pointerWidthPx else 0F
    return (markerWidthPx - pointerAdjustmentPx).coerceAtLeast(0F)
}

internal fun rangeSpan(range: ClosedFloatingPointRange<Float>): Float {
    return range.endInclusive - range.start
}

internal fun mapRangeOrStart(
    value: Float,
    originalRange: ClosedFloatingPointRange<Float>,
    targetRange: ClosedFloatingPointRange<Float>
): Float {
    val originalSpan = rangeSpan(originalRange)
    if (originalSpan == 0F) return targetRange.start

    return (value - originalRange.start) / originalSpan * rangeSpan(targetRange) + targetRange.start
}
