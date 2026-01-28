package com.rafambn.framebar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

data class Marker(
    val size: DpSize,
    val topOffset: Dp,
    val color: Color = Color.Gray,
    val bitmap: ImageBitmap? = null
)