package com.rafambn.framebar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Marker(
    val width: Dp = 5.dp,
    val height: Dp = 5.dp,
    val topOffset: Dp = 0.dp,
    val color: Color = Color.Gray,
    val bitmap: ImageBitmap? = null
)