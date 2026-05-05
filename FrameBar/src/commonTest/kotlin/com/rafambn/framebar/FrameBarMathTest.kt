package com.rafambn.framebar

import com.rafambn.framebar.enums.CoercePointer
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FrameBarMathTest {
    @Test
    fun markerCenterOffsetForIndexUsesPixelWidths() {
        val markerWidthsPx = listOf(20F, 30F, 50F)

        assertFloatEquals(10F, markerCenterOffsetForIndexPx(0F, markerWidthsPx))
        assertFloatEquals(35F, markerCenterOffsetForIndexPx(1F, markerWidthsPx))
        assertFloatEquals(75F, markerCenterOffsetForIndexPx(2F, markerWidthsPx))
    }

    @Test
    fun markerIndexForOffsetUsesPixelStartOffsets() {
        val startOffsetsPx = markerStartOffsetsPx(listOf(20F, 30F, 50F))

        assertEquals(0F, markerIndexForOffsetPx(-10F, startOffsetsPx))
        assertEquals(0F, markerIndexForOffsetPx(19.99F, startOffsetsPx))
        assertEquals(1F, markerIndexForOffsetPx(20F, startOffsetsPx))
        assertEquals(2F, markerIndexForOffsetPx(50F, startOffsetsPx))
    }

    @Test
    fun effectiveTrackWidthNeverGoesNegative() {
        val markerWidthsPx = listOf(20F, 30F)

        assertFloatEquals(
            expected = 0F,
            actual = effectiveTrackWidthPx(
                markerWidthsPx = markerWidthsPx,
                coercedPointer = CoercePointer.COERCED,
                pointerWidthPx = 80F
            )
        )
    }

    @Test
    fun mapRangeOrStartHandlesZeroWidthOriginalRange() {
        val mapped = mapRangeOrStart(
            value = 5F,
            originalRange = 0F..0F,
            targetRange = 10F..20F
        )

        assertFloatEquals(10F, mapped)
        assertTrue(mapped.isFinite())
    }

    @Test
    fun dragDeltaMappingUsesRangeSpanNotRangeStart() {
        val valueRange = 50F..100F
        val deltaValue = mapRangeOrStart(
            value = 10F,
            originalRange = 0F..100F,
            targetRange = 0F..rangeSpan(valueRange)
        )

        assertFloatEquals(5F, deltaValue)
    }

    private fun assertFloatEquals(expected: Float, actual: Float) {
        assertTrue(
            actual = abs(expected - actual) < 0.0001F,
            message = "Expected <$expected>, actual <$actual>."
        )
    }
}
